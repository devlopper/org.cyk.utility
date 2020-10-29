package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractProducerConsumer extends AbstractObject implements Runnable,Serializable {

	protected String name;
	protected Integer numberOfRunnablesToBeExecuted;
	protected List<Runnable> runnables,executedRunnables;
	protected ExecutorService executorService;
	protected Map<Runnable,Future<?>> futuresMap;
	protected Collection<Future<?>> processedFutures;
	protected Integer numberOfRunnablesExecuted;
	protected Listener listener;
	protected Thread thread;
	protected Long timeout;
	protected TimeUnit timeoutUnit;
	
	public List<Runnable> getRunnables(Boolean injectIfNull) {
		if(runnables == null && Boolean.TRUE.equals(injectIfNull))
			runnables = new ArrayList<>();
		return runnables;
	}
	
	public AbstractProducerConsumer addRunnables(List<Runnable> runnables) {
		if(CollectionHelper.isEmpty(runnables))
			return this;
		getRunnables(Boolean.TRUE).addAll(runnables);
		return this;
	}
	
	public AbstractProducerConsumer addRunnables(Runnable...runnables) {
		if(ArrayHelper.isEmpty(runnables))
			return this;
		return addRunnables(CollectionHelper.listOf(runnables));
	}
	
	@Override
	public void run() {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("numberOfRunnablesToBeExecuted", numberOfRunnablesToBeExecuted);
		//ThrowableHelper.throwIllegalArgumentExceptionIfNull("listener", listener);
		Long t = System.currentTimeMillis();
		Integer numberOfRunnablesToBeExecuted = this.numberOfRunnablesToBeExecuted;
		Integer numberOfRunnablesExecuted = ValueHelper.defaultToIfNull(this.numberOfRunnablesExecuted,0);
		LogHelper.logInfo(String.format("%s has started. #execution=%s", name,numberOfRunnablesToBeExecuted), getClass());
		do {			
			if(executorService == null) {
				if(CollectionHelper.isNotEmpty(runnables)) {
					Runnable runnable = runnables.remove(0);
					try {
						if(listener != null)
							listener.listenStart(this, runnable);
						runnable.run();
						if(executedRunnables == null)
							executedRunnables = new ArrayList<>();
						executedRunnables.add(runnable);
						if(listener != null)
							listener.listenCompletion(this, runnable);
					} catch (Exception exception) {
						LogHelper.log(exception, getClass());
					}
					numberOfRunnablesExecuted = numberOfRunnablesExecuted + 1;
				}
			}else {
				if(CollectionHelper.isNotEmpty(runnables)) {
					do {
						Runnable runnable = runnables.remove(0);
						if(listener != null)
							listener.listenStart(this, runnable);
						Future<?> future = executorService.submit(runnable);
						if(futuresMap == null)
							futuresMap = new HashMap<>();
						futuresMap.put(runnable, future);
					}while(!runnables.isEmpty());
				}
				if(MapHelper.isEmpty(futuresMap)) {
					numberOfRunnablesExecuted = 0;
				}else {
					numberOfRunnablesExecuted = (int) futuresMap.values().stream().filter(future -> future.isDone()).count();
					for(Map.Entry<Runnable, Future<?>> entry : futuresMap.entrySet())
						if(entry.getValue().isDone() && !CollectionHelper.contains(processedFutures, entry.getValue())) {
							if(executedRunnables == null)
								executedRunnables = new ArrayList<>();
							executedRunnables.add(entry.getKey());
							if(listener != null)
								listener.listenCompletion(this, entry.getKey());
							if(processedFutures == null)
								processedFutures = new ArrayList<>();
							processedFutures.add(entry.getValue());
						}
				}
			}
		}while(numberOfRunnablesExecuted < numberOfRunnablesToBeExecuted);
		if(executorService != null) {
			LogHelper.logInfo("Shuting down executor service", getClass());
			executorService.shutdown();
			if(timeout != null && timeoutUnit != null)
				try {
					executorService.awaitTermination(timeout, timeoutUnit);
				} catch (InterruptedException exception) {
					LogHelper.log(exception, getClass());
				}
		}
		Long duration = System.currentTimeMillis() - t;
		String durationAsString = Duration.ofMillis(duration).toString();
		durationAsString = durationAsString.substring(2);
		LogHelper.logInfo(String.format("%s has done. duration = %s", name,durationAsString), getClass());
	}
	
	/**/
	
	public AbstractProducerConsumer start() {
		thread = new Thread(this, name);
		thread.start();
		LogHelper.logInfo(String.format("thread of %s has started", name), getClass());
		return this;
	}

	public AbstractProducerConsumer join() {
		LogHelper.logInfo(String.format("joining thread of %s", name), getClass());
		try {
			thread.join();
		} catch (InterruptedException exception) {
			LogHelper.log(exception, getClass());
		}
		return this;
	}
	
	/**/
	
	public static interface Listener {
		void listenStart(AbstractProducerConsumer producerConsumer,Runnable runnable);
		void listenCompletion(AbstractProducerConsumer producerConsumer,Runnable runnable);
		
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			@Override
			public void listenStart(AbstractProducerConsumer producerConsumer, Runnable runnable) {
				
			}
			@Override
			public void listenCompletion(AbstractProducerConsumer producerConsumer, Runnable runnable) {
				
			}			
		}
	}
}