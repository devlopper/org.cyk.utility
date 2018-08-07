package org.cyk.utility.function;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.Assertion;
import org.cyk.utility.assertion.AssertionBuilder;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends org.cyk.utility.__kernel__.function.AbstractFunctionImpl<INPUT, OUTPUT> implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeVerifyPreConditions__() {
		super.__executeVerifyPreConditions__();
		ExecutionPhase executionPhase = getPreExecutionPhase();
		if(executionPhase!=null){
			Collection<Assertion> assertions = executionPhase.getAssertions();	
			if(__injectCollectionHelper__().isNotEmpty(assertions)){
				for(Assertion index : assertions){
					if(Boolean.FALSE.equals(index.getValue()))
						__injectThrowableHelper__().throwRuntimeException(index.getMessageWhenValueIsNotTrue());
				}
			}
			
			Collection<Runnable> runnables = executionPhase.getRunnables();
			if(__injectCollectionHelper__().isNotEmpty(runnables)){
				for(Runnable index : runnables){
					index.run();
				}
			}
		}
		
	}
	
	@Override
	protected void __executeVerifyPostConditions__() {
		super.__executeVerifyPostConditions__();
	}
	
	@Override
	public Function<INPUT, OUTPUT> setInput(INPUT input) {
		getProperties().setInput(input);
		return this;
	}

	@Override
	protected void afterExecute() {
		super.afterExecute();
		getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter("duration", getProperties().getFromPath(Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION));
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
	
	@Override
	public ExecutionPhase getPreExecutionPhase() {
		return (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,Properties.PRE);
	}
	@Override
	public Function<INPUT, OUTPUT> setPreExecutionPhase(ExecutionPhase executionPhase) {
		getProperties().setFromPath(new Object[]{Properties.EXECUTION,Properties.PRE}, executionPhase);
		return this;
	}
	
	@Override
	public ExecutionPhase getPostExecutionPhase() {
		return (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,Properties.POST);
	}
	@Override
	public Function<INPUT, OUTPUT> setPostExecutionPhase(ExecutionPhase executionPhase) {
		getProperties().setFromPath(new Object[]{Properties.EXECUTION,Properties.POST}, executionPhase);
		return this;
	}
	
	/**/
	
	@Override
	public Function<INPUT, OUTPUT> addExecutionPhaseAssertions(Boolean isPre,AssertionBuilder...assertionBuilders){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,pre);
		if(executionPhase == null)
			getProperties().setFromPath(new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		executionPhase.addAssertionBuilders(assertionBuilders);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addExecutionPhaseRunnables(Boolean isPre,Runnable...runnables){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) getProperties().getFromPath(Properties.EXECUTION,pre);
		if(executionPhase == null)
			getProperties().setFromPath(new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		executionPhase.addRunnables(runnables);
		return this;
	}
	
	/**/
	
	protected StringHelper __injectStringHelper__(){
		return __inject__(StringHelper.class);
	}
	
	protected ValueHelper __injectValueHelper__(){
		return __inject__(ValueHelper.class);
	}
	
	protected CollectionHelper __injectCollectionHelper__(){
		return __inject__(CollectionHelper.class);
	}
	
	protected ThrowableHelper __injectThrowableHelper__(){
		return __inject__(ThrowableHelper.class);
	}
}
