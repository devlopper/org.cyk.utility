package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends AbstractObject implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Function<INPUT,OUTPUT> execute() {
		Long start = System.currentTimeMillis();
		getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.START}, start);
		
		OUTPUT output = null;
		Runnable runnable = (Runnable) getProperties().getRunnable();
		try {
			if(runnable == null){
				output = __execute__();
			}else {
				runnable.run();
			}
		} catch (Exception exception) {
			if(Boolean.TRUE.equals(getIsCatchThrowable()))
				getProperties().setThrowable(exception);	
			else
				throw new RuntimeException(exception);
		}
		Long end = System.currentTimeMillis();
		getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.END}, end);
		getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION}, end - start);
		getProperties().setOutput(output);
		afterExecute();
		return this;
	}
	
	protected OUTPUT __execute__() throws Exception {
		throw new RuntimeException("Implementation or runnable required");
	}
	
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
	
	@Override
	public Boolean getIsCatchThrowable() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.CATCH,Properties.THROWABLE);
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsCatchThrowable(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.CATCH,Properties.THROWABLE}, value);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addChild(Object... children) {
		return (Function<INPUT, OUTPUT>) super.addChild(children);
	}
	
	@Override
	public Function<INPUT, OUTPUT> addChildren(Collection<Object> children) {
		return (Function<INPUT, OUTPUT>) super.addChildren(children);
	}
}
