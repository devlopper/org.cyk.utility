package org.cyk.utility.test;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface Test extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	/*
	 * Setup
	 */
	
	Test setup();
	
	/*
	 * Running
	 */
	
	/*
	 * Cleanup
	 */
	
	Test clean();
	
	/**/
	
	Throwable getThrowable();
	
	/* Assertion */
	
	Test assertThrowableCauseIsInstanceOf(Class<?> aClass);
	Test assertThrowableIsNull();
}
