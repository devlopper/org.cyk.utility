package org.cyk.utility.runnable;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.thread.ExecutorServiceBuilder;

public class RunnablesExecutorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements RunnablesExecutor,Objectable {
	private static final long serialVersionUID = 1L;

	private Runnables runnables;
	private ExecutorServiceBuilder executorServiceBuilder;
	private Long timeOut;
	private TimeUnit timeOutUnit;

	@Override
	protected void ____execute____() throws Exception {
		Runnables runnables = getRunnables();
		if(__inject__(CollectionHelper.class).isNotEmpty(runnables)) {
			ExecutorServiceBuilder threadPoolExecutorBuilder = getExecutorServiceBuilder();
			if(threadPoolExecutorBuilder == null)
				threadPoolExecutorBuilder = __inject__(ExecutorServiceBuilder.class);
			ExecutorService executorService = threadPoolExecutorBuilder.execute().getOutput();
			for(Runnable index : runnables.get())
				executorService.submit(index);
			executorService.shutdown();
			Long timeOut = getTimeOut();
			if(timeOut == null)
				timeOut = 10l;
			TimeUnit timeOutUnit = getTimeOutUnit();
			if(timeOutUnit == null)
				timeOutUnit = TimeUnit.SECONDS;
			try {
				if(executorService.awaitTermination(timeOut, timeOutUnit))
					;
				else
					System.out.println("RUNNABLES EXECUTOR TIME OUT!!!");
			} catch (InterruptedException e) {
				
				throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public ExecutorServiceBuilder getExecutorServiceBuilder() {
		return executorServiceBuilder;
	}
	
	@Override
	public ExecutorServiceBuilder getExecutorServiceBuilder(Boolean injectIfNull) {
		return (ExecutorServiceBuilder) __getInjectIfNull__(FIELD_EXECUTOR_SERVICE_BUILDER, injectIfNull);
	}
	
	@Override
	public RunnablesExecutor setExecutorServiceBuilder(ExecutorServiceBuilder executorServiceBuilder) {
		this.executorServiceBuilder = executorServiceBuilder;
		return this;
	}
	
	@Override
	public Runnables getRunnables() {
		return runnables;
	}

	@Override
	public Runnables getRunnables(Boolean injectIfNull) {
		return (Runnables) __getInjectIfNull__(FIELD_RUNNABLES, injectIfNull);
	}

	@Override
	public RunnablesExecutor setRunnables(Runnables runnables) {
		this.runnables = runnables;
		return this;
	}

	@Override
	public RunnablesExecutor addRunnables(Collection<Runnable> runnables) {
		getRunnables(Boolean.TRUE).add(runnables);
		return this;
	}

	@Override
	public RunnablesExecutor addRunnables(Runnable... runnables) {
		getRunnables(Boolean.TRUE).add(runnables);
		return this;
	}
	
	@Override
	public Long getTimeOut() {
		return timeOut;
	}
	
	@Override
	public RunnablesExecutor setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
		return this;
	}
	
	@Override
	public TimeUnit getTimeOutUnit() {
		return timeOutUnit;
	}
	
	@Override
	public RunnablesExecutor setTimeOutUnit(TimeUnit timeOutUnit) {
		this.timeOutUnit = timeOutUnit;
		return this;
	}
	
	private static final String FIELD_RUNNABLES = "runnables";
	private static final String FIELD_EXECUTOR_SERVICE_BUILDER = "executorServiceBuilder";
	
}
