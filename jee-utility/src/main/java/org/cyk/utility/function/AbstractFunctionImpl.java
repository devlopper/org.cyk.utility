package org.cyk.utility.function;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.function.FunctionRunnable;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.assertion.AssertionBuilder;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.type.TypeHelper;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends org.cyk.utility.__kernel__.function.AbstractFunctionImpl<INPUT, OUTPUT> implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;
	
	//private Collection<Class<?>> assertionsProvidersDomainsClasses;
	
	@Override
	public Function<INPUT, OUTPUT> setInput(INPUT input) {
		getProperties().setInput(input);
		return this;
	}
	
	@Override
	protected void __log__() {
		super.__log__();
		getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE)
			.addParameter("duration", getProperties().getFromPath(Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION));
		Class<?> callerClass = getCallerClass();
		if(callerClass !=null)
			getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter("caller class", callerClass);
		//TODO log is not printed when following are uncommented
		//Object callerIdentifier = getCallerIdentifier();
		//if(callerIdentifier !=null)
		//	getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter("calleridentifier", callerIdentifier);
		
		Set<Class<?>> assertionsProviderClasses = new LinkedHashSet<>();
		if(getExecutionPhaseTry()!=null && getExecutionPhaseTry().getBegin()!=null && getExecutionPhaseTry().getBegin().getAssertionsProvider()!=null)
			assertionsProviderClasses.add(getExecutionPhaseTry().getBegin().getAssertionsProvider().getClass());
		if(getExecutionPhaseTry()!=null && getExecutionPhaseTry().getEnd()!=null && getExecutionPhaseTry().getEnd().getAssertionsProvider()!=null)
			assertionsProviderClasses.add(getExecutionPhaseTry().getEnd().getAssertionsProvider().getClass());
		
		if(!assertionsProviderClasses.isEmpty())
			getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter("Pre/Post Assertions Provider Classes", assertionsProviderClasses);
		
		Log log = getLog();
		if(log!=null && Boolean.TRUE.equals(getLoggable()))
			log.execute();
	}

	@Override
	public OUTPUT getOutput() {
		return (OUTPUT) getProperties().getOutput();
	}
	
	@Override
	public Function<INPUT,OUTPUT> setProperties(Properties properties) {
		return (Function<INPUT,OUTPUT>) super.setProperties(properties);
	}
	
	@Override
	public Log getLog() {
		return (Log) getProperties().getLog();
	}
	
	@Override
	public Log getLog(Boolean injectIfNull) {
		Log log = getLog();
		if(log == null && Boolean.TRUE.equals(injectIfNull))
			setLog(log = __injectLog__());
		return log;
	}
	
	protected Log __injectLog__() {
		return ____inject____(Log.class).setLevel(LogLevel.TRACE).setSourceClassName(StringUtils.substringBefore(getClass().getName(),CharacterConstant.DOLLAR.toString()))
				.setSourceMethodName("execute");
	}
	
	@Override
	public Function<INPUT, OUTPUT> setLog(Log log) {
		getProperties().setLog(log);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addLogMessageBuilderParameter(Object key, Object value) {
		getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter(key, value);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addLogMessageBuilderParameter(Object parameter) {
		getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter(parameter);
		return this;
	}
	
	@Override
	public Boolean getLoggable() {
		return (Boolean) getProperties().getLoggable();
	}
	
	public Function<INPUT,OUTPUT> setLoggable(Boolean loggable) {
		getProperties().setLoggable(loggable);
		return this;
	}
	
	/*@Override
	public Function<INPUT, OUTPUT> setLogMarkers(Collection<String> markers) {
		getProperties().setMarkers(markers);
		return this;
	}
	
	/*@Override
	public Collection<String> getLogMarkers() {
		return (Collection<String>) getProperties().getMarkers();
	}
	
	@Override
	public Function<INPUT, OUTPUT> addLogMarkers(Collection<String> markers) {
		if(__inject__(CollectionHelper.class).isNotEmpty(markers)){
			Collection<String> collection = getLogMarkers();
			if(collection == null)
				setLogMarkers(collection = new ArrayList<>());
			collection.addAll(markers);
		}
		
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addLogMarkers(String... markers) {
		addLogMarkers(__inject__(CollectionHelper.class).instanciate(markers));
		return this;
	}*/
	
	@Override
	public Boolean getMonitorable() {
		return (Boolean) getProperties().getMonitorable();
	}
	
	public Function<INPUT,OUTPUT> setMonitorable(Boolean monitorable) {
		getProperties().setLoggable(monitorable);
		return this;
	}
	/*
	@Override
	public Collection<Class<?>> getAssertionsProvidersDomainsClasses() {
		return assertionsProvidersDomainsClasses;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setAssertionsProvidersDomainsClasses(Collection<Class<?>> domainsClasses) {
		this.assertionsProvidersDomainsClasses = domainsClasses;
		return this;
	}
	*/
	@Override @Deprecated
	public ExecutionPhase getPreExecutionPhase() {
		return (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,Properties.PRE);
	}
	@Override @Deprecated
	public Function<INPUT, OUTPUT> setPreExecutionPhase(ExecutionPhase executionPhase) {
		getProperties().setFromPath(new Object[]{Properties.EXECUTION,Properties.PRE}, executionPhase);
		return this;
	}
	
	@Override @Deprecated
	public ExecutionPhase getPostExecutionPhase() {
		return (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,Properties.POST);
	}
	@Override @Deprecated
	public Function<INPUT, OUTPUT> setPostExecutionPhase(ExecutionPhase executionPhase) {
		getProperties().setFromPath(new Object[]{Properties.EXECUTION,Properties.POST}, executionPhase);
		return this;
	}
	
	/**/
	
	@Override @Deprecated
	public Function<INPUT, OUTPUT> addExecutionPhaseAssertions(Boolean isPre,AssertionBuilder...assertionBuilders){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,pre);
		if(executionPhase == null)
			getProperties().setFromPath(new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		executionPhase.addAssertionBuilders(assertionBuilders);
		return this;
	}
	
	@Override @Deprecated
	public Function<INPUT, OUTPUT> addExecutionPhaseRunnables(Boolean isPre,Runnable...runnables){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,pre);
		if(executionPhase == null)
			getProperties().setFromPath(new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		executionPhase.addRunnables(runnables);
		return this;
	}
	
	@Override @Deprecated
	public Function<INPUT, OUTPUT> addExecutionPhaseFunctionRunnables(Boolean isPre,FunctionRunnable<?>...functionRunnables){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,pre);
		if(executionPhase == null)
			getProperties().setFromPath(new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		executionPhase.addFunctionRunnables(functionRunnables);
		
		if(__inject__(ArrayHelper.class).isNotEmpty(functionRunnables)) {
			for(FunctionRunnable<?> index : functionRunnables)
				if(index.getFunction() == null)
					try {
						//TODO must be integrated in FieldValueSetter
						MethodUtils.invokeMethod(index, "setFunction", this);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
		}
		
		return this;
	}
	
	/**/
	
	protected static StringHelper __injectStringHelper__(){
		return __inject__(StringHelper.class);
	}
	
	protected static ValueHelper __injectValueHelper__(){
		return __inject__(ValueHelper.class);
	}
	
	protected static CollectionHelper __injectCollectionHelper__(){
		return __inject__(CollectionHelper.class);
	}
	
	protected static ClassHelper __injectClassHelper__(){
		return __inject__(ClassHelper.class);
	}
	
	protected static FieldHelper __injectFieldHelper__(){
		return __inject__(FieldHelper.class);
	}
	
	protected static FieldValueGetter __injectFieldValueGetter__(){
		return __inject__(FieldValueGetter.class);
	}
	
	protected ThrowableHelper __injectThrowableHelper__(){
		return __inject__(ThrowableHelper.class);
	}
	
	protected NumberHelper __injectNumberHelper__(){
		return __inject__(NumberHelper.class);
	}
	
	protected InstanceHelper __injectInstanceHelper__(){
		return __inject__(InstanceHelper.class);
	}
	
	protected TypeHelper __injectTypeHelper__(){
		return __inject__(TypeHelper.class);
	}
}
