package org.cyk.utility.log.log4j2;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.log.AbstractLogImpl;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.value.ValueHelper;

@Dependent @Log4j2
public class LogLog4j2Impl extends AbstractLogImpl<Level> implements LogLog4j2, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Level level, String sourceClassName, String sourceMethodName, LogMessage message,Throwable throwable) {
		Logger logger = LogManager.getLogger(sourceClassName);
		Marker marker = (Marker) __getMarker__(getMarkers());
		String template = message == null ? null : message.getTemplate();
		
		if(throwable == null){
			if(message != null){
				Object[] parameters = __inject__(ArrayHelper.class).instanciate(message.getArguments());
				logger.log(level,marker, template,parameters);
			}			
		}else
			logger.catching(throwable);
	}
	
	@Override
	protected Level __getLevel__(LogLevel level) {
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
		if(CollectionHelper.isNotEmpty(markers)){
			for(Object index : markers)
				if(marker == null)
					marker = MarkerManager.getMarker(index.toString());
				else
					marker = MarkerManager.getMarker(index.toString()).setParents(marker);
		}
		return marker;
	}
}
