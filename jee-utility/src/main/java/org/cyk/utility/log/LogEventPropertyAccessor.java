package org.cyk.utility.log;

import org.cyk.utility.__kernel__.log.LogLevel;

public interface LogEventPropertyAccessor {

	String getMessage(Object bean);
	LogLevel getLevel(Object bean);
	Object getMarker(Object bean);
	
}
