package org.cyk.utility.runnable;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.thread.ExecutorServiceBuilder;

public interface RunnablesExecutor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Runnables getRunnables();
	Runnables getRunnables(Boolean injectIfNull);
	RunnablesExecutor setRunnables(Runnables runnables);
	RunnablesExecutor addRunnables(Collection<Runnable> runnables);
	RunnablesExecutor addRunnables(Runnable...runnables);
	
	ExecutorServiceBuilder getExecutorServiceBuilder();
	ExecutorServiceBuilder getExecutorServiceBuilder(Boolean injectIfNull);
	RunnablesExecutor setExecutorServiceBuilder(ExecutorServiceBuilder executorServiceBuilder);
	
	Long getTimeOut();
	RunnablesExecutor setTimeOut(Long timeOut);
	
	TimeUnit getTimeOutUnit();
	RunnablesExecutor setTimeOutUnit(TimeUnit timeOutUnit);
	
}
