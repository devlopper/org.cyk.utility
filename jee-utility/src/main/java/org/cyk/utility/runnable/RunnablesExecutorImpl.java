package org.cyk.utility.runnable;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.thread.ExecutorServiceBuilder;

@Dependent @Deprecated
public class RunnablesExecutorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements RunnablesExecutor,Objectable {
	private static final long serialVersionUID = 1L;

	private Runnables runnables;
	private ExecutorServiceBuilder executorServiceBuilder;
	private Long timeOut;
	private TimeUnit timeOutUnit;

	@Override
	public Function<Properties, Void> execute() {
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
				timeOut = 60l * 2;
			TimeUnit timeOutUnit = getTimeOutUnit();
			if(timeOutUnit == null)
				timeOutUnit = TimeUnit.SECONDS;
			try {
				if(executorService.awaitTermination(timeOut, timeOutUnit))
					;
				else
					System.out.println("RUNNABLES EXECUTOR TIME OUT!!!");
			} catch (InterruptedException exception) {
				throw new RuntimeException(exception);
			}
		}
		return this;
	}
	
	@Override
	public ExecutorServiceBuilder getExecutorServiceBuilder() {
		return executorServiceBuilder;
	}
	
	@Override
	public ExecutorServiceBuilder getExecutorServiceBuilder(Boolean injectIfNull) {
		if(executorServiceBuilder == null && Boolean.TRUE.equals(injectIfNull))
			executorServiceBuilder = __inject__(ExecutorServiceBuilder.class);
		return executorServiceBuilder;
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
		if(runnables == null && Boolean.TRUE.equals(injectIfNull))
			runnables = __inject__(Runnables.class);
		return runnables;
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
}
