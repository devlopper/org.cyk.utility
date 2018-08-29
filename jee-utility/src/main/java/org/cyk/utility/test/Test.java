package org.cyk.utility.test;

import java.util.Collection;

import org.cyk.utility.__kernel__.function.FunctionRunnable;
import org.cyk.utility.assertion.AssertionBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface Test extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	String getName();
	Test setName(String name);
	
	/*
	 * Setup
	 */
	
	Test setObjectsToBeCreated(Collection<Object> objects);
	Collection<Object> getObjectsToBeCreated();
	Test addObjectsToBeCreatedCollection(Collection<Object> objects);
	Test addObjectsToBeCreatedArray(Object...objects);

	/*
	 * Running
	 */
	

	/* 
	 * Results 
	 */
	
	Throwable getThrowable();
	
	/* 
	 * Assertions 
	 */

	Test setExpectedThrowableCauseClass(Class<? extends Throwable> aClass);
	Class<? extends Throwable> getExpectedThrowableCauseClass();
	Test setExpectedThrowableCauseClassIsConstraintViolationException();
	
	Test assertThrowableCauseIsInstanceOf(Class<?> aClass);
	Test assertThrowableIsNull();
	
	Test assertThrowableCauseIsInstanceOfConstraintViolationException();

	/*
	 * Cleanup
	 */
	Test setGarbages(Collection<Object> objects);
	Collection<Object> getGarbages();
	Test addGarbagesCollection(Collection<Object> objects);
	Test addGarbagesArray(Object...objects);
	
	/**/
	
	@Override Test execute();
	@Override Test addExecutionPhaseRunnables(Boolean isPre, Runnable... runnables);
	@Override Test addExecutionPhaseAssertions(Boolean isPre,AssertionBuilder... assertionBuilders);
	@Override Test addExecutionPhaseFunctionRunnables(Boolean isPre, FunctionRunnable<?>... functionRunnables);
	
	@Override Test addFinallyRunnablesInTryCatchFinally(Runnable... runnables);
}
