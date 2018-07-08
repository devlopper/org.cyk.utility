package org.cyk.utility.log.jul;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.log.AbstractLogImpl;
import org.cyk.utility.log.LogEventEntityBuilder;
import org.cyk.utility.log.LogEventRepository;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.log.message.LogMessageBuilder;
import org.cyk.utility.value.ValueHelper;

public class LogJulImpl extends AbstractLogImpl implements LogJul, Serializable {
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
		if(aClass == null)
			aClass = getClass();
		Logger logger = Logger.getLogger(aClass.getName());
		logger.setLevel(Level.ALL);
		logger.addHandler(new java.util.logging.Handler(){

			@Override
			public void publish(LogRecord record) {
				__inject__(LogEventRepository.class).add(__inject__(LogEventEntityBuilder.class).execute(record).getOutput());
			}

			@Override public void flush() {}
			@Override public void close() throws SecurityException {}
			
		});
		Level level = (Level) __getLevel__(getLevel());
		String template = message == null ? null : message.getTemplate();
		
		Throwable throwable = getThrowable();
		if(throwable == null){
			if(message != null){
				Object[] parameters = __inject__(ArrayHelper.class).instanciate(message.getArguments());
				if(parameters == null)
					parameters = new Object[]{};
				logger.log(level, template, parameters);
			}			
		}else
			logger.log(level,throwable.getMessage(),throwable);
		return null;
	}

	@Override
	protected Object __getLevel__(LogLevel level) {
		level = __inject__(ValueHelper.class).defaultToIfNull(level, LogLevel.DEFAULT);
		switch(level){
		case ALL: return Level.ALL;
		case TRACE: return Level.FINEST;
		case DEBUG: return Level.FINE;
		case INFO: return Level.INFO;
		case WARN: return Level.WARNING;
		case ERROR: return Level.SEVERE;
		case FATAL: 
			//TODO log warning
			return Level.SEVERE;
		case OFF: 
			//TODO log warning
			return Level.OFF;
		}
		return null;
	}
	
	@Override
	protected Object __getMarker__(Collection<Object> markers) {
		return null;
	}
}
