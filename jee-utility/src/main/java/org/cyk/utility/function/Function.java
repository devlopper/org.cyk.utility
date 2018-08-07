package org.cyk.utility.function;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionBuilder;
import org.cyk.utility.log.Log;

public interface Function<INPUT,OUTPUT> extends org.cyk.utility.__kernel__.function.Function<INPUT, OUTPUT> {

	Function<INPUT,OUTPUT> setInput(INPUT input);
	OUTPUT getOutput();
	
	Function<INPUT,OUTPUT> setProperties(Properties properties);
	
	Function<INPUT,OUTPUT> setLog(Log log);
	Log getLog();
	Log getLog(Boolean injectIfNull);
	Function<INPUT,OUTPUT> addLogMessageBuilderParameter(Object parameter);
	Function<INPUT,OUTPUT> addLogMessageBuilderParameter(Object key,Object value);
	
	Function<INPUT,OUTPUT> setLoggable(Boolean loggable);
	Boolean getLoggable();
	
	Function<INPUT,OUTPUT> setMonitorable(Boolean monitorable);
	Boolean getMonitorable();
	
	ExecutionPhase getPreExecutionPhase();
	Function<INPUT,OUTPUT> setPreExecutionPhase(ExecutionPhase executionPhase);
	
	ExecutionPhase getPostExecutionPhase();
	Function<INPUT,OUTPUT> setPostExecutionPhase(ExecutionPhase executionPhase);
	
	Function<INPUT, OUTPUT> addExecutionPhaseAssertions(Boolean isPre,AssertionBuilder...assertionBuilders);
	Function<INPUT, OUTPUT> addExecutionPhaseRunnables(Boolean isPre,Runnable...runnables);
}
