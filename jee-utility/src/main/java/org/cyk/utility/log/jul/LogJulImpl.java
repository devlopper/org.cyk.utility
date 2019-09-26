package org.cyk.utility.log.jul;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.log.AbstractLogImpl;
import org.cyk.utility.log.LogEventEntityBuilder;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.__kernel__.value.ValueHelper;

@Dependent
public class LogJulImpl extends AbstractLogImpl<Level> implements LogJul, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Level level, String sourceClassName, String sourceMethodName, LogMessage message,Throwable throwable) {
		Logger logger = Logger.getLogger(sourceClassName);
		if(INITIALSED.contains(logger.getName())){
			
		}else {
			logger.setLevel(Level.ALL);
			logger.addHandler(new java.util.logging.Handler(){

				@Override
				public void publish(LogRecord record) {
					__inject__(LogEventEntityRepository.class).add(__inject__(LogEventEntityBuilder.class).execute(record).getOutput());
				}

				@Override public void flush() {}
				@Override public void close() throws SecurityException {}
				
			});	
			INITIALSED.add(logger.getName());
		}
		if(throwable == null){
			if(message != null){
				Object[] parameters = __inject__(ArrayHelper.class).instanciate(message.getArguments());
				if(parameters == null)
					parameters = new Object[]{};
				logger.logp(level,sourceClassName,sourceMethodName, message == null ? null : message.getTemplate(), parameters);
			}			
		}else
			logger.log(level,throwable.getMessage(),throwable);
	}
	/*
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
		String sourceClassName = aClass.getName();
		String sourceMethodName = "THE_METH";
		Logger logger = Logger.getLogger(aClass.getName());
		if(INITIALSED.contains(logger.getName())){
			
		}else {
			logger.setLevel(Level.ALL);
			logger.addHandler(new java.util.logging.Handler(){

				@Override
				public void publish(LogRecord record) {
					__inject__(LogEventEntityRepository.class).add(__inject__(LogEventEntityBuilder.class).execute(record).getOutput());
				}

				@Override public void flush() {}
				@Override public void close() throws SecurityException {}
				
			});	
			INITIALSED.add(logger.getName());
		}
		
		Level level = (Level) __getLevel__(getLevel());
		String template = message == null ? null : message.getTemplate();
		
		Throwable throwable = getThrowable();
		if(throwable == null){
			if(message != null){
				Object[] parameters = __inject__(ArrayHelper.class).instanciate(message.getArguments());
				if(parameters == null)
					parameters = new Object[]{};
				logger.logp(level,sourceClassName,sourceMethodName, template, parameters);
			}			
		}else
			logger.log(level,throwable.getMessage(),throwable);
		return null;
	}
*/
	@Override
	protected Level __getLevel__(LogLevel level) {
		level = ValueHelper.defaultToIfNull(level, LogLevel.DEFAULT);
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
	
	/**/
	
	private static final Set<String> INITIALSED = new HashSet<>();
}
