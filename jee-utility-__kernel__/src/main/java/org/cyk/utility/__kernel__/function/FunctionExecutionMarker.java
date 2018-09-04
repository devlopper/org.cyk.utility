package org.cyk.utility.__kernel__.function;

public interface FunctionExecutionMarker<T> {

	/*
	 * We use <process> to avoid conflict when using <execute>
	 */
	T process();
	
}
