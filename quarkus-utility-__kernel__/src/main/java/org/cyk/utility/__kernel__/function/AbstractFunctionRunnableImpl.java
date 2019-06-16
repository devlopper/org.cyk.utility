package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractFunctionRunnableImpl<FUNCTION extends Function<?,?>> extends AbstractObject implements FunctionRunnable<FUNCTION>,Serializable {
	private static final long serialVersionUID = 1L;
		
	@SuppressWarnings("unchecked")
	@Override
	public FUNCTION getFunction() {
		return (FUNCTION) getProperties().getFunction();
	}

	@Override
	public FunctionRunnable<FUNCTION> setFunction(FUNCTION function) {
		getProperties().setFunction(function);
		return this;
	}

	@Override
	public Runnable getRunnable() {
		return (Runnable) getProperties().getRunnable();
	}

	@Override
	public FunctionRunnable<FUNCTION> setRunnable(Runnable runnable) {
		getProperties().setRunnable(runnable);
		return this;
	}

	@Override
	public Object getOutput() {
		return getProperties().getOutput();
	}

	@Override
	public FunctionRunnable<FUNCTION> setOutput(Object output) {
		getProperties().setOutput(output);
		return this;
	}

}
