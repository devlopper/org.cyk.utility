package org.cyk.utility.log.log4j2;

import java.io.Serializable;
import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.log.AbstractLogImpl;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.log.message.LogMessageBuilder;
import org.cyk.utility.value.ValueHelper;

public class LogLog4jImpl extends AbstractLogImpl implements LogLog4j, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Void __execute__() {
		LogMessage message = null;
		if(getProperties().getMessage() instanceof LogMessage){
			message = (LogMessage) getProperties().getMessage();
		}else{
			LogMessageBuilder messageBuilder = getMessageBuilder();
			if(messageBuilder == null){
				//TODO log error
			}else{
				message = messageBuilder.execute().getOutput();
			}
		}
		
		Class<?> aClass = (Class<?>) getProperties().getClazz();
		org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(aClass);
		Level level = (Level) __getLevel__(getLevel());
		Marker marker = (Marker) __getMarker__(getMarkers());
		String template = message == null ? null : message.getTemplate();
		
		
		Throwable throwable = getThrowable();
		if(throwable == null){
			if(message != null){
				Object[] parameters = __inject__(ArrayHelper.class).instanciate(message.getArguments());
				logger.log(level,marker, template,parameters);
			}			
		}else
			logger.catching(throwable);
		return null;
	}

	@Override
	protected Object __getLevel__(LogLevel level) {
		level = __inject__(ValueHelper.class).defaultToIfNull(level, LogLevel.DEFAULT);
		switch(level){
		case ALL: return org.apache.logging.log4j.Level.ALL;
		case TRACE: return org.apache.logging.log4j.Level.TRACE;
		case DEBUG: return org.apache.logging.log4j.Level.DEBUG;
		case INFO: return org.apache.logging.log4j.Level.INFO;
		case WARN: return org.apache.logging.log4j.Level.WARN;
		case ERROR: return org.apache.logging.log4j.Level.ERROR;
		case FATAL: return org.apache.logging.log4j.Level.FATAL;
		case OFF: return org.apache.logging.log4j.Level.OFF;
		}
		return null;
	}
	
	@Override
	protected Marker __getMarker__(Collection<Object> markers) {
		Marker marker = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(markers)){
			for(Object index : markers)
				if(marker == null)
					marker = MarkerManager.getMarker(index.toString());
				else
					marker = MarkerManager.getMarker(index.toString()).setParents(marker);
		}
		return marker;
	}
}
