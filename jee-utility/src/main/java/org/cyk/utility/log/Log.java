package org.cyk.utility.log;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithVoidAsOutput;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.log.message.LogMessageBuilder;

public interface Log extends FunctionWithVoidAsOutput<LogMessage> {

	LogMessageBuilder getMessageBuilder();
	LogMessageBuilder getMessageBuilder(Boolean instanciateIfNull);
	Log setMessageBuilder(LogMessageBuilder builder);
	
	LogMessage getMessage();
	Log setMessage(LogMessage message);
	
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
