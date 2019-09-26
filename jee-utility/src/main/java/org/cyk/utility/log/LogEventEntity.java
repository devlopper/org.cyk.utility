package org.cyk.utility.log;

import java.util.Collection;

import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface LogEventEntity extends Objectable {

	LogLevel getLevel();
	LogEventEntity setLevel(LogLevel logLevel);
	
	String getMessage();
	LogEventEntity setMessage(String message);
	
	Collection<String> getMarkers();
	LogEventEntity setMarkers(Collection<String> markers);
	
	Object getMarker();
	LogEventEntity setMarker(Object marker);
}
