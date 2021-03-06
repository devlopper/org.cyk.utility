package org.cyk.utility.common.cdi;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.RunnableListener;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2448439169984218703L;

	public static Boolean SYSTEM_OUT_LOG_TRACE = Boolean.FALSE;
	
	protected CommonUtils commonUtils = CommonUtils.getInstance();
	protected ListenerUtils listenerUtils = ListenerUtils.getInstance();
	
	@Getter protected Collection<BeanListener> beanListeners = new ArrayList<>();
	@Getter protected Long __orderNumber__;
	//@Getter protected Boolean __initialized__;
	protected Properties propertiesMap;
	private transient Logger __logger__; 
	
	@PostConstruct
	public void postConstruct(){
		beforeInitialisation();
		initialisation();
		afterInitialisation();
	}
	
	protected void beforeInitialisation(){}
	
	protected void initialisation(){}

	protected void afterInitialisation(){}
	
	public AbstractBean set__orderNumber__(Long __orderNumber__){
		this.__orderNumber__ = __orderNumber__;
		return this;
	}

	public Properties getPropertiesMap() {
		if(propertiesMap==null)
			propertiesMap = instanciateProperties();
		return propertiesMap;
	}
	
	public <T> T getPropertyInstanciateIfNull(Object key,Class<T> aClass){
		return getPropertiesMap().getInstanciateIfNull(key, aClass);
	}
	
	public AbstractBean computeAndSetPropertyFromStringIdentifier(Object key,String identifier,Object[] parameters){
		getPropertiesMap().computeAndSetPropertyFromStringIdentifier(key, identifier, parameters);
		return this;
	}
	
	public AbstractBean computeAndSetPropertyFromStringIdentifier(Object key,String identifier){
		return computeAndSetPropertyFromStringIdentifier(key,identifier,new Object[]{});
	}
	
	public AbstractBean computeAndSetPropertyValueFromStringIdentifier(String identifier,Object[] parameters){
		computeAndSetPropertyFromStringIdentifier(Properties.VALUE, identifier, parameters);
		return this;
	}
	
	public AbstractBean computeAndSetPropertyValueFromStringIdentifier(String identifier){
		return computeAndSetPropertyValueFromStringIdentifier(identifier,new Object[]{});
	}
	
	protected Properties instanciateProperties(){
		Properties propertiesMap = new Properties();
		Properties.setDefaultValues(getClass(), propertiesMap);
		listenPropertiesInstanciated(propertiesMap);
		return propertiesMap;
	}
	
	protected void listenPropertiesInstanciated(Properties propertiesMap){
		for(BeanListener listener : BeanListener.COLLECTION)
			listener.propertiesMapInstanciated(this,propertiesMap);
	}
	
	protected <T> Collection<T> castCollection(Collection<?> collection,Class<T> aClass){
		return commonUtils.castCollection(collection, aClass);
	}
	
	protected <T> T getReference(BeanManager aBeanManager,Class<T> aClass){
		Set<Bean<?>> beans = aBeanManager.getBeans(aClass);
		@SuppressWarnings("unchecked")
		Bean<T> bean = (Bean<T>) aBeanManager.resolve(beans);
		CreationalContext<T> context = aBeanManager.createCreationalContext(bean);
		@SuppressWarnings("unchecked")
		T result = (T) aBeanManager.getReference(bean, aClass, context);
		return result;
	}
	
	protected <T> T getReference(Class<T> aClass){
		return getReference(getBeanManager(), aClass);
	}
	
	protected BeanManager getBeanManager(){
		return CDI.current().getBeanManager();
	}
	
	public static <T> T inject(Class<T> aClass){
		return CommonUtils.getInstance().inject(aClass);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T inject(Class<T> aClass,Context context,String scope,String namespace,String interfaceName,String implementationNameSuffix){
		try {
			Object object = context.lookup("java:"+scope+"/"+namespace+"/"+interfaceName+implementationNameSuffix);
			return (T) PortableRemoteObject.narrow(object, aClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected <T> T inject(Class<T> aClass,Context context,String namespace){
		return inject(aClass, context, "global", namespace, aClass.getSimpleName(),"Impl");
	}
	
	protected <T> T newInstance(Class<T> aClass){
		try {
			return aClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	protected <T> T newInstance(Class<T> aClass,Class<?>[] paramClasses,Object[] paramValues){
		try {
			return aClass.getConstructor(paramClasses).newInstance(paramValues);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String fieldPath(String...names){
		return StringUtils.join(names,".");
	}
	
	@SuppressWarnings("unchecked")
	protected <TYPE> Class<TYPE> parameterizedClass(Class<TYPE> classType,Integer index){
	    return (Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	protected void pause(long millisecond){
		commonUtils.pause(millisecond);
	}
	
	protected <T> T getValueIfNotNullElseDefault(Class<T> valueClass,T value,T defaultValue){
		return commonUtils.getValueIfNotNullElseDefault(valueClass, value, defaultValue);
	}
	
	protected <T> Class<T> castClass(Class<?> inputClass,Class<T> outputClass){
		return commonUtils.castClass(inputClass, outputClass);
	}
	
	/**/
	
	protected String __info__(String message,int lineLength){
		return StringUtils.rightPad(message, lineLength);
	}
	
	protected void __writeInfo__(String message,int lineLength,String separator){
		if(separator==null)
			message = __info__(message, lineLength);
		else
			message = __info__(message, lineLength)+separator;
		for(BeanListener beanListener : beanListeners)
			beanListener.info(message);
	}
	
	protected void __writeInfo__(String message,int lineLength){
		__writeInfo__(message, lineLength, null);
	}
	
	protected void __writeInfoStart__(String message){
		__writeInfo__(message, 40, " : ");
	}
	
	protected void __writeInfoStartOK__(){
		__writeInfo__("OK");
	}
	
	protected void __writeInfo__(String message){
		__writeInfo__(message, 0, null);
	}
	
	protected void debug(Object object){
		System.out.println("------------------------------------- Debug -----------------------------");
		if(object instanceof Matcher){
			Matcher matcher = (Matcher) object;
			for(int i=1;i<=matcher.groupCount();i++)
				System.out.println("Group "+i+" = "+matcher.group(i));
		}else
			System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}
	
	protected void logStackTrace(){
		try {
			throw new RuntimeException("Stack Trace");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if(ArrayUtils.isEmpty(stackTraceElements)){
			System.out.println("No stack trace to log.");
			return;
		}
		StackTraceElement stackTraceElement = stackTraceElements[0];
		System.out.println(stackTraceElement.getClassName()+" - "+stackTraceElement.getMethodName()+" - "+stackTraceElement.getLineNumber());
		*/
	}
	
	protected void logStackTraceAsString(Set<String> packages){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if(ArrayUtils.isEmpty(stackTraceElements)){
			System.out.println("No stack trace to log.");
			return;
		}
		List<String> messages = new ArrayList<>();
		for(StackTraceElement stackTraceElement : stackTraceElements){
			Boolean add = Boolean.FALSE;
			if(packages==null || packages.isEmpty()){
				add = Boolean.TRUE;
			}else{
				for(String p : packages){
					if(StringUtils.startsWith(stackTraceElement.getClassName(), p)){
						add = Boolean.TRUE;
						break;
					}
				}
			}
			if(Boolean.TRUE.equals(add)){
				messages.add(/*stackTraceElement.getClassName()+" - "+*/stackTraceElement.getMethodName()+"("+stackTraceElement.getLineNumber()+")");
			}
		}
		logDebug(StringUtils.join(messages,">"));
	}
	
	protected void logStackTraceAsString(String _package){
		logStackTraceAsString(new LinkedHashSet<>(Arrays.asList(_package)));
	}
	
	protected void logStackTraceAsString(){
		logStackTraceAsString(new LinkedHashSet<String>());
	}
	
	protected static <T extends java.lang.Throwable> void throw__(ConditionHelper.Condition.Builder builder,Class<T> causeClass){
		ThrowableHelper.getInstance().throw_(builder,causeClass);
	}
	
	protected static <T extends java.lang.Throwable> void throw__(Class<T> causeClass,ConditionHelper.Condition.Builder...builders){
		ThrowableHelper.getInstance().throw_(causeClass,builders);
	}
	
	protected static <T extends java.lang.Throwable> void throw__(ConditionHelper.Condition.Builder...builders){
		ThrowableHelper.getInstance().throw_(builders);
	}
	
	protected static <T extends java.lang.Throwable> void throw__(Collection<ConditionHelper.Condition.Builder> builders){
		ThrowableHelper.getInstance().throw_(builders);
	}
	
	/**/
	
	protected static <RUNNABLE extends Runnable> void runnableStarted(RUNNABLE aRunnable,Collection<RunnableListener<RUNNABLE>> listeners){
		Long time = System.currentTimeMillis();
		for(RunnableListener<RUNNABLE> listener : listeners)
			listener.started(aRunnable,time);
	}
	
	protected static <RUNNABLE extends Runnable> void runnableStopped(RUNNABLE aRunnable,Collection<RunnableListener<RUNNABLE>> listeners){
		Long time = System.currentTimeMillis();
		for(RunnableListener<RUNNABLE> listener : listeners)
			listener.stopped(aRunnable,time);
	}
	
	protected static <RUNNABLE extends Runnable> void runnableThrowable(RUNNABLE aRunnable,Collection<RunnableListener<RUNNABLE>> listeners,Throwable throwable){
		for(RunnableListener<RUNNABLE> listener : listeners)
			listener.throwable(aRunnable,throwable);
	}
	
	/**/
	
	protected Execution __watchExecute__(String name,Runnable aRunnable){
		Execution execution;
		execution = new Execution(name);
		execution.start();
		aRunnable.run();
		execution.end();
		return execution;
	}
	
	@Getter
	protected class Execution{
		private String name;
		private long startTimestamp;
		private long duration;
		
		public void start(){
			__writeInfoStart__(name);
			startTimestamp = System.currentTimeMillis();
		}
		
		public void end(){
			duration = System.currentTimeMillis()-startTimestamp;
			__writeInfoStartOK__();
		}

		public Execution(String name) {
			super();
			this.name = name;
		}
		@Override
		public String toString() {
			return name+" , "+duration+" , "+(duration/1000)+"s , "+" , "+(duration/1000/60)+"min";
		}
	}
	
	protected Logger __logger__(){
		if(__logger__==null)
			__logger__ = LogManager.getLogger(getClass());
		return __logger__;
	}
	
	protected void logError(Object message,Object...arguments) {
		if(message==null)
			return;
		String stringMessage = message.toString();
		__logger__().error(stringMessage,arguments);
	}

	protected void logWarning(Object message,Object...arguments) {
		if(message==null)
			return;
		String stringMessage = message.toString();
		__logger__().warn(stringMessage,arguments);
	}

	protected void logInfo(Object message,Object...arguments) {
		if(message==null)
			return;
		String stringMessage = message.toString();
		__logger__().info(stringMessage,arguments);
	}

	protected void logTrace(Object message,Object...arguments) {
		if(message==null)
			return;
		String stringMessage = message.toString();
		__logger__().trace(stringMessage,arguments);
		systemOut(SYSTEM_OUT_LOG_TRACE,message, arguments);
	}
	
	protected void logTrace(LoggingHelper.Message message) {
		logTrace(message.getTemplate(), CollectionHelper.getInstance().getArray(message.getArguments()));
	}
		
	protected void logTrace(LoggingHelper.Message.Builder loggingMessageBuilder) {
		if(loggingMessageBuilder==null)
			return;
		LoggingHelper.Message message = loggingMessageBuilder.execute();
		logTrace(message);
	}
	
	protected void logTrace(Action<?, ?> action) {
		if(action==null)
			return;
		logTrace(action.getLoggingMessageBuilder());
	}

	protected void logDebug(Object message,Object...arguments) {
		if(message==null)
			return;
		String stringMessage = message.toString();
		__logger__().debug(stringMessage,arguments);
	}

	protected void logThrowable(Throwable throwable){
		logError(throwable.toString());
	}
	
	protected ClassLoader getClassLoader(){
		return this.getClass().getClassLoader();
	}
	
	private static void systemOut(Boolean condition,Object message,Object...arguments) {
		if(Boolean.TRUE.equals(condition))
			System.out.println(message+" : "+StringUtils.join(arguments," , "));
	}
	
	protected void addLoggingMessageBuilderParameters(LoggingHelper.Message.Builder loggingMessageBuilder,Object...parameters){
		if(loggingMessageBuilder==null)
			return;
		loggingMessageBuilder.addParameters(parameters);
	}
	
	protected void throwNotYetImplemented(){
		throw new RuntimeException("Not yet implemented");
	}
	
	/**/
	
	public static final String FIELD___ORDER_NUMBER__ = "__orderNumber__";
}
