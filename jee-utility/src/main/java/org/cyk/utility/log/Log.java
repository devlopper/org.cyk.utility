package org.cyk.utility.log;

import java.util.Collection;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.log.message.LogMessageBuilder;

public interface Log extends Function<LogMessage,Void> {

	LogMessageBuilder getMessageBuilder();
	LogMessageBuilder getMessageBuilder(Boolean instanciateIfNull);
	Log setMessageBuilder(LogMessageBuilder builder);
	Log addMessageParameter(Object parameter);
	Log addMessageParameter(Object key,Object value);
	
	LogMessage getMessage();
	Log setMessage(LogMessage message);
	
	String getSourceClassName();
	Log setSourceClassName(String sourceClassName);
	
	String getSourceMethodName();
	Log setSourceMethodName(String sourceMethodName);
	
	Throwable getThrowable();
	Log setThrowable(Throwable throwable);
	
	LogLevel getLevel();
	Log setLevel(LogLevel level);
	
	Collection<Object> getMarkers();
	Log setMarkers(Collection<Object> markers);
	Log addMarkers(Collection<?> markers);
	Log addMarkers(String...markers);

	/* execution shortcuts */
	
	Log execute(String message,LogLevel level);
	Log executeInfo(String message);
	Log executeWarn(String message);
	Log executeTrace(String message);
	Log executeDebug(String message);
	
	Log executeThrowable(Throwable throwable);
}
