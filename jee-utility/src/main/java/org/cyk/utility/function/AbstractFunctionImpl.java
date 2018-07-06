package org.cyk.utility.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.log.Log;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends org.cyk.utility.__kernel__.function.AbstractFunctionImpl<INPUT, OUTPUT> implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Function<INPUT, OUTPUT> setInput(INPUT input) {
		getProperties().setInput(input);
		return this;
	}

	@Override
	protected void afterExecute() {
		super.afterExecute();
		__getLog__().getMessageBuilder(Boolean.TRUE).addParameter("duration", getProperties().getFromPath(Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION));
		if(Boolean.TRUE.equals(getLoggable()))
			__getLog__().addMarkers(getLogMarkers()).execute();
	}

	protected abstract OUTPUT __execute__();
	
	@SuppressWarnings("unchecked")
	@Override
	public OUTPUT getOutput() {
		return (OUTPUT) getProperties().getOutput();
	}
	
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
	public Function<INPUT, OUTPUT> setLogMarkers(Collection<String> markers) {
		getProperties().setMarkers(markers);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getLogMarkers() {
		return (Collection<String>) getProperties().getMarkers();
	}
	
	@Override
	public Function<INPUT, OUTPUT> addLogMarkers(Collection<String> markers) {
		if(__inject__(CollectionHelper.class).isNotEmpty(markers)){
			Collection<String> collection = getLogMarkers();
			if(collection == null)
				setLogMarkers(collection = new ArrayList<>());
			collection.addAll(markers);
		}
		
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addLogMarkers(String... markers) {
		addLogMarkers(__inject__(CollectionHelper.class).instanciate(markers));
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
