package org.cyk.utility.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.function.AbstractFunctionImpl;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.log.message.LogMessage;
import org.cyk.utility.log.message.LogMessageBuilder;


public abstract class AbstractLogImpl<LEVEL> extends AbstractFunctionImpl<LogMessage,Void> implements Log,Serializable {
	private static final long serialVersionUID = 8661933611010350759L;

	@Override
	protected Void __execute__() {
		StackTraceElement sourceStackTraceElement = __inject__(StackTraceHelper.class).getAt(5);
		LEVEL level = (LEVEL) __getLevel__(getLevel());
		Class<?> aClass = (Class<?>) getProperties().getClazz();
		if(aClass == null)
			aClass = getClass();
		String sourceClassName = getSourceClassName();
		if(StringHelper.isBlank(sourceClassName)){
			if(aClass != null)
				sourceClassName = aClass.getName();
			if(StringHelper.isBlank(sourceClassName)){
				sourceClassName = sourceStackTraceElement.getClassName();
			}
		}
		String sourceMethodName = getSourceMethodName();
		if(StringHelper.isBlank(sourceMethodName)){
			sourceMethodName = sourceStackTraceElement.getMethodName();
		}
		LogMessage message = null;
		if(getProperties().getMessage() instanceof LogMessage){
			message = (LogMessage) getProperties().getMessage();
		}else{
			LogMessageBuilder messageBuilder = getMessageBuilder();
			if(messageBuilder == null){
				//TODO log warning
			}else{
				message = messageBuilder.execute().getOutput();
			}
		}
		__execute__(level, sourceClassName, sourceMethodName, message, getThrowable());
		return null;
	}
	
	protected abstract void __execute__(LEVEL level,String sourceClassName,String sourceMethodName,LogMessage message,Throwable throwable);
	
	@Override
	public LogMessageBuilder getMessageBuilder(Boolean instanciateIfNull) {
		LogMessageBuilder messageBuilder = getMessageBuilder();
		if(messageBuilder == null && Boolean.TRUE.equals(instanciateIfNull)){
			messageBuilder = __inject__(LogMessageBuilder.class);
			messageBuilder.setParent(this);
			setMessageBuilder(messageBuilder);
		}
		return messageBuilder;
	}
	
	@Override
	public LogMessageBuilder getMessageBuilder() {
		LogMessageBuilder builder = null;
		if(getProperties().getMessage() instanceof Properties){
			builder = (LogMessageBuilder) ((Properties)getProperties().getMessage()).getBuilder();
		}else{
			
		}
		return builder;
	}
	
	@Override
	public Log setMessageBuilder(LogMessageBuilder builder) {
		if(getProperties().getMessage() == null){
			getProperties().setMessage(new Properties());
		}
		if(getProperties().getMessage() instanceof Properties){
			((Properties)getProperties().getMessage()).setBuilder(builder);
		}else{
			
		}
		return this;
	}
	
	@Override
	public Log addMessageParameter(Object parameter) {
		getMessageBuilder(Boolean.TRUE).addParameter(parameter);
		return this;
	}
	
	@Override
	public Log addMessageParameter(Object key, Object value) {
		getMessageBuilder(Boolean.TRUE).addParameter(key, value);
		return this;
	}
	
	protected abstract LEVEL __getLevel__(LogLevel level);
	protected abstract Object __getMarker__(Collection<Object> markers);
	
	@Override
	public String getSourceClassName() {
		return (String) getProperties().getFromPath(Properties.SOURCE,Properties.CLASS,Properties.NAME);
	}
	
	@Override
	public Log setSourceClassName(String sourceClassName) {
		getProperties().setFromPath(new Object[]{Properties.SOURCE,Properties.CLASS,Properties.NAME}, sourceClassName);
		return this;
	}
	
	@Override
	public String getSourceMethodName() {
		return (String) getProperties().getFromPath(Properties.SOURCE,Properties.METHOD,Properties.NAME);
	}
	
	@Override
	public Log setSourceMethodName(String sourceClassName) {
		getProperties().setFromPath(new Object[]{Properties.SOURCE,Properties.METHOD,Properties.NAME}, sourceClassName);
		return this;
	}
	
	@Override
	public Log setThrowable(Throwable throwable) {
		getProperties().setThrowable(throwable);
		return this;
	}
	
	@Override
	public Throwable getThrowable() {
		return (Throwable) getProperties().getThrowable();
	}
	
	@Override
	public LogMessage getMessage() {
		return (LogMessage) getProperties().getMessage();
	}
	
	@Override
	public Log setMessage(LogMessage message) {
		getProperties().setMessage(message);
		return this;
	}
	
	@Override
	public LogLevel getLevel() {
		return (LogLevel) getProperties().getLogLevel();
	}
	
	@Override
	public Log setLevel(LogLevel level) {
		getProperties().setLogLevel(level);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getMarkers() {
		return (Collection<Object>) getProperties().getMarkers();
	}
	
	@Override
	public Log setMarkers(Collection<Object> markers) {
		getProperties().setMarkers(markers);
		return this;
	}
	
	@Override
	public Log addMarkers(Collection<?> markers) {
		if(CollectionHelper.isNotEmpty(markers)){
			Collection<Object> collection = getMarkers();
			if(collection == null)
				setMarkers(collection = new ArrayList<>());
			collection.addAll(markers);
		}
		return this;
	}
	
	@Override
	public Log addMarkers(String... markers) {
		addMarkers(List.of(markers));
		return this;
	}
	
	/**/
	
	@Override
	public Log execute(String message, LogLevel level) {
		setLevel(level).getMessageBuilder(Boolean.TRUE).addParameter(message).getParent().execute();
		return this;
	}
	
	@Override
	public Log executeInfo(String message) {
		return execute(message, LogLevel.INFO);
	}
	
	@Override
	public Log executeWarn(String message) {
		return execute(message, LogLevel.WARN);
	}
	
	@Override
	public Log executeTrace(String message) {
		return execute(message, LogLevel.TRACE);
	}
	
	@Override
	public Log executeDebug(String message) {
		return execute(message, LogLevel.DEBUG);
	}
	
	@Override
	public Log executeThrowable(Throwable throwable) {
		setLevel(LogLevel.ERROR).setThrowable(throwable).execute();
		return null;
	}
}
