package org.cyk.utility.log;

public interface LogEventPropertyAccessor {

	String getMessage(Object bean);
	LogLevel getLevel(Object bean);
	Object getMarker(Object bean);
	
}
