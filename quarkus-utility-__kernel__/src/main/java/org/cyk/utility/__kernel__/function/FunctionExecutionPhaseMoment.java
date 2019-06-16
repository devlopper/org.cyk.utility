package org.cyk.utility.__kernel__.function;

import java.util.Collection;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.assertion.AssertionBuilder;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FunctionExecutionPhaseMoment extends Objectable {

	Function<?,Collection<Assertion>> getAssertionsProvider();
	FunctionExecutionPhaseMoment setAssertionsProvider(Function<?,Collection<Assertion>> assertionsProvider);
	
	Collection<Assertion> getAssertions();
	FunctionExecutionPhaseMoment setAssertions(Collection<Assertion> assertions);
	FunctionExecutionPhaseMoment addAssertions(Collection<Assertion> assertions);
	FunctionExecutionPhaseMoment addAssertions(Assertion...assertions);
	
	Collection<AssertionBuilder> getAssertionBuilders();
	FunctionExecutionPhaseMoment setAssertionBuilders(Collection<AssertionBuilder> assertionBuilders);
	FunctionExecutionPhaseMoment addAssertionBuilders(Collection<AssertionBuilder> assertionBuilders);
	FunctionExecutionPhaseMoment addAssertionBuilders(AssertionBuilder...assertionBuilders);
	
	Collection<Runnable> getRunnables();
	Collection<Runnable> getRunnables(Boolean instanciateIfNull);
	FunctionExecutionPhaseMoment setRunnables(Collection<Runnable> runnables);
	FunctionExecutionPhaseMoment addRunnables(Collection<Runnable> runnables);
	FunctionExecutionPhaseMoment addRunnables(Runnable...runnables);
	
	Collection<FunctionRunnable<?>> getFunctionRunnables();
	FunctionExecutionPhaseMoment setFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables);
	FunctionExecutionPhaseMoment addFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables);
	FunctionExecutionPhaseMoment addFunctionRunnables(FunctionRunnable<?>...functionRunnables);

	FunctionExecutionPhaseMoment run();
	
	Boolean getRunned();
	FunctionExecutionPhaseMoment setRunned(Boolean runned);
	
	@Override FunctionExecutionPhase getParent();
}
