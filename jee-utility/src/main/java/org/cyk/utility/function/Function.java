package org.cyk.utility.function;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

public interface Function<INPUT,OUTPUT> extends org.cyk.utility.__kernel__.function.Function<INPUT, OUTPUT> {

	Function<INPUT,OUTPUT> setInput(INPUT input);
	OUTPUT getOutput();
	
	Function<INPUT,OUTPUT> setProperties(Properties properties);
	
	Function<INPUT,OUTPUT> setLoggable(Boolean loggable);
	Boolean getLoggable();
	
	Function<INPUT,OUTPUT> setLogMarkers(Collection<String> markers);
	Collection<String> getLogMarkers();
	Function<INPUT,OUTPUT> addLogMarkers(Collection<String> markers);
	Function<INPUT,OUTPUT> addLogMarkers(String...markers);
	
	Function<INPUT,OUTPUT> setMonitorable(Boolean monitorable);
	Boolean getMonitorable();
}
