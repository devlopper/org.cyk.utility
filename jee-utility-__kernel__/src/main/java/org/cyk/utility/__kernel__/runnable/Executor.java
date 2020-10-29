package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Executor<RUNNABLE extends Runnable> extends AbstractObject implements Runnable,Serializable {

	private String name;
	private Integer numberOfRunnablesToBeExecuted;
	private Queue<RUNNABLE> runnables;
	private ExecutorService executorService;
	private Map<RUNNABLE,Future<?>> futuresMap;
	private Collection<Future<?>> processedFutures;
	private Integer numberOfRunnablesExecuted;
	private Listener<RUNNABLE> listener;
	private Thread thread;
	private Long timeout;
	private TimeUnit timeoutUnit;
	
	private Executor<?> input,output;
	
	public Queue<RUNNABLE> getRunnables(Boolean injectIfNull) {
		if(runnables == null && Boolean.TRUE.equals(injectIfNull))
			runnables = new LinkedBlockingQueue<>();
		return runnables;
	}
	
	public Executor<RUNNABLE> addRunnables(List<RUNNABLE> runnables) {
		if(CollectionHelper.isEmpty(runnables))
			return this;
		getRunnables(Boolean.TRUE).addAll(runnables);
		return this;
	}
	
	public Executor<RUNNABLE> addRunnables(RUNNABLE...runnables) {
		if(ArrayHelper.isEmpty(runnables))
			return this;
		return addRunnables(CollectionHelper.listOf(runnables));
	}
	
	private synchronized RUNNABLE pollRunnable() {
		if(CollectionHelper.isEmpty(runnables))
			return null;
		RUNNABLE runnable = runnables.poll();
		if(runnable == null)
			LogHelper.logWarning("Null runnable found", getClass());
		return runnable;
	}
	
	@Override
	public void run() {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("numberOfRunnablesToBeExecuted", numberOfRunnablesToBeExecuted);
		Long t = System.currentTimeMillis();
		Integer numberOfRunnablesToBeExecuted = this.numberOfRunnablesToBeExecuted;
		Integer numberOfRunnablesExecuted = ValueHelper.defaultToIfNull(this.numberOfRunnablesExecuted,0);
		LogHelper.logInfo(String.format("%s has started. #execution=%s", name,numberOfRunnablesToBeExecuted), getClass());
		do {			
			if(executorService == null) {
				if(CollectionHelper.isNotEmpty(runnables)) {
					RUNNABLE runnable = pollRunnable();
					try {
						if(listener != null)
							listener.listenStart(this, runnable);
						runnable.run();
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
						RUNNABLE runnable = pollRunnable();
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
					for(Map.Entry<RUNNABLE, Future<?>> entry : futuresMap.entrySet())
						if(entry.getValue().isDone() && !CollectionHelper.contains(processedFutures, entry.getValue())) {
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
			LogHelper.logFine("Shuting down executor service", getClass());
			executorService.shutdown();
			if(timeout != null && timeoutUnit != null)
				try {
					executorService.awaitTermination(timeout, timeoutUnit);
				} catch (InterruptedException exception) {
					LogHelper.log(exception, getClass());
				}
		}
		Long duration = System.currentTimeMillis() - t;
		LogHelper.logInfo(String.format("%s has done. duration = %s", name,TimeHelper.formatDuration(duration)), getClass());
	}
	
	/**/
	
	public Executor<RUNNABLE> start() {
		thread = new Thread(this, name);
		thread.start();
		LogHelper.logInfo(String.format("Thread of %s has started", name), getClass());
		return this;
	}

	public Executor<RUNNABLE> join() {
		LogHelper.logInfo(String.format("Joining thread of %s", name), getClass());
		try {
			thread.join();
		} catch (InterruptedException exception) {
			LogHelper.log(exception, getClass());
		}
		return this;
	}
	
	/**/
	
	public static interface Listener<RUNNABLE extends Runnable> {
		void listenStart(Executor<RUNNABLE> producerConsumer,RUNNABLE runnable);
		void listenCompletion(Executor<RUNNABLE> producerConsumer,RUNNABLE runnable);
		
		/**/
		
		public static abstract class AbstractImpl<RUNNABLE extends Runnable> extends AbstractObject implements Listener<RUNNABLE>,Serializable {
			@Override
			public void listenStart(Executor<RUNNABLE> producerConsumer, RUNNABLE runnable) {
				
			}
			@Override
			public void listenCompletion(Executor<RUNNABLE> producerConsumer, RUNNABLE runnable) {
				
			}			
		}
	}
}