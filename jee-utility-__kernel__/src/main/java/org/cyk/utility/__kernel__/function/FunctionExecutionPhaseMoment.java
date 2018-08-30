package org.cyk.utility.__kernel__.function;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FunctionExecutionPhaseMoment extends Objectable {

	Collection<Runnable> getRunnables();
	FunctionExecutionPhaseMoment setRunnables(Collection<Runnable> runnables);
	FunctionExecutionPhaseMoment addRunnables(Collection<Runnable> runnables);
	FunctionExecutionPhaseMoment addRunnables(Runnable...runnables);
	
	Collection<FunctionRunnable<?>> getFunctionRunnables();
	FunctionExecutionPhaseMoment setFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables);
	FunctionExecutionPhaseMoment addFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables);
	FunctionExecutionPhaseMoment addFunctionRunnables(FunctionRunnable<?>...functionRunnables);

	FunctionExecutionPhaseMoment run();
}
