package org.cyk.utility.log.slf4j;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.inject.Alternative;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.log.AbstractLogImpl;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.value.ValueHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;

@Alternative
public class LogSlf4jImpl extends AbstractLogImpl<Level> implements LogSlf4j, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Level level, String sourceClassName, String sourceMethodName, LogMessage message,Throwable throwable) {
		Logger logger = LoggerFactory.getLogger(sourceClassName);
		Marker marker = (Marker) __getMarker__(getMarkers());
		String template = message == null ? null : message.getTemplate();
		if(throwable == null){
			if(message != null){
				Object[] parameters = __inject__(ArrayHelper.class).instanciate(message.getArguments());
				if(parameters == null)
					parameters = new Object[]{};
				try {
					MethodUtils.invokeMethod(logger, level.name().toLowerCase(), marker,template,parameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}else
			logger.error(marker,throwable.getMessage(),throwable);
	}

	@Override
	protected Level __getLevel__(LogLevel level) {
		level = __inject__(ValueHelper.class).defaultToIfNull(level, LogLevel.DEFAULT);
		switch(level){
		case ALL: 
			//TODO log warning
			return org.slf4j.event.Level.TRACE;
		case TRACE: return Level.TRACE;
		case DEBUG: return Level.DEBUG;
		case INFO: return Level.INFO;
		case WARN: return Level.WARN;
		case ERROR: return Level.ERROR;
		case FATAL: 
			//TODO log warning
			return Level.ERROR;
		case OFF: 
			//TODO log warning
			return null;
		}
		return null;
	}
	
	@Override
	protected Marker __getMarker__(Collection<Object> markers) {
		Marker marker = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(markers)){
			for(Object index : markers)
				if(marker == null)
					marker = MarkerFactory.getMarker(index.toString());
				else
					marker = MarkerFactory.getMarker(marker.getName()+" > "+index.toString());//.setParents(marker);
		}
		return marker;
	}
}
