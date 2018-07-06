package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends AbstractObject implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public final Function<INPUT,OUTPUT> execute() {
		Long start = System.currentTimeMillis();
		getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.START}, start);
		OUTPUT output = __execute__();
		Long end = System.currentTimeMillis();
		getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.END}, end);
		getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION}, end - start);
		getProperties().setOutput(output);
		afterExecute();
		return this;
	}
	
	protected abstract OUTPUT __execute__();
	
	protected void afterExecute(){
		
	}
	
	@Override
	public Function<INPUT, OUTPUT> setInput(INPUT input) {
		getProperties().setInput(input);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OUTPUT getOutput() {
		return (OUTPUT) getProperties().getOutput();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT,OUTPUT> setProperties(Properties properties) {
		return (Function<INPUT,OUTPUT>) super.setProperties(properties);
	}
	
}
