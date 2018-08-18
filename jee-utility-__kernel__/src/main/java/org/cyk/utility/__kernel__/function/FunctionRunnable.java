package org.cyk.utility.__kernel__.function;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FunctionRunnable<FUNCTION extends Function<?,?>> extends Objectable {

	FUNCTION getFunction();
	FunctionRunnable<FUNCTION> setFunction(FUNCTION function);
	
	Runnable getRunnable();
	FunctionRunnable<FUNCTION> setRunnable(Runnable runnable);
	
	Object getOutput();
	FunctionRunnable<FUNCTION> setOutput(Object output);
}
