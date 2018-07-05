package org.cyk.utility.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.log.Log;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends AbstractObject implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Function<INPUT, OUTPUT> setInput(INPUT input) {
		getProperties().setInput(input);
		return this;
	}

	@Override
	public final Function<INPUT,OUTPUT> execute() {
		Long start = System.currentTimeMillis();
		OUTPUT output = __execute__();
		Long end = System.currentTimeMillis();
		Long duration = end - start;
		__getLog__().getMessageBuilder(Boolean.TRUE).addParameter("duration", duration);
		getProperties().setOutput(output);
		if(Boolean.TRUE.equals(getLoggable()))
			__getLog__().execute();
		return this;
	}
	
	protected abstract OUTPUT __execute__();
	
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
	
	protected Log __getLog__(){
		Log log = (Log) getProperties().getLog();
		if(log == null)
			getProperties().setLog( log = __inject__(Log.class));
		return log;
	}
	
	@Override
	public Boolean getLoggable() {
		return (Boolean) getProperties().getLoggable();
	}
	
	public Function<INPUT,OUTPUT> setLoggable(Boolean loggable) {
		getProperties().setLoggable(loggable);
		return this;
	}
	
	@Override
	public Boolean getMonitorable() {
		return (Boolean) getProperties().getMonitorable();
	}
	
	public Function<INPUT,OUTPUT> setMonitorable(Boolean monitorable) {
		getProperties().setLoggable(monitorable);
		return this;
	}
}
