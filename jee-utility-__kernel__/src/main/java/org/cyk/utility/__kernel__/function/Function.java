package org.cyk.utility.__kernel__.function;

import java.util.Collection;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.properties.Properties;

public interface Function<INPUT,OUTPUT> extends Objectable {

	Function<INPUT,OUTPUT> setInput(INPUT input);
	Function<INPUT,OUTPUT> execute();
	Function<INPUT,OUTPUT> executeWithOneParameter(Object parameter1);
	void executeWithOneParameterToReturnVoid(Object parameter1);
	void executeToReturnVoid();
	OUTPUT getOutput();
	<T extends OUTPUT> T getOutputAs(Class<T> aClass);
	
	@Override Function<INPUT,OUTPUT> setParent(Object parent);
	
	Function<INPUT,OUTPUT> setProperties(Properties properties);
	
	Function<INPUT,OUTPUT> setIsExecutable(Boolean value);
	Boolean getIsExecutable();
	
	Function<INPUT,OUTPUT> setIsCatchThrowable(Boolean value);
	Boolean getIsCatchThrowable();
	
	Function<INPUT,OUTPUT> addChild(Object... child);
	Function<INPUT,OUTPUT> addChildren(Collection<Object> children);
	
	Function<INPUT,OUTPUT> setPreConditionsAssertionsProvider(Function<?, Collection<Assertion>> assertionsProvider);
	Function<?, Collection<Assertion>> getPreConditionsAssertionsProvider();
	
	Function<INPUT,OUTPUT> setPostConditionsAssertionsProvider(Function<?, Collection<Assertion>> assertionsProvider);
	Function<?, Collection<Assertion>> getPostConditionsAssertionsProvider();
	
	@Deprecated Function<INPUT,OUTPUT> setRunnable(Runnable runnable);
	@Deprecated Runnable getRunnable();
	
	@Deprecated Function<INPUT,OUTPUT> setFinallyRunnablesInTryCatchFinally(Collection<Runnable> runnables);
	@Deprecated Collection<Runnable> getFinallyRunnablesInTryCatchFinally();
	@Deprecated Function<INPUT,OUTPUT> addFinallyRunnablesInTryCatchFinally(Collection<Runnable> runnables);
	@Deprecated Function<INPUT,OUTPUT> addFinallyRunnablesInTryCatchFinally(Runnable...runnables);
	
	Function<INPUT,OUTPUT> setCallerClass(Class<?> aClass);
	Class<?> getCallerClass();
	
	Function<INPUT,OUTPUT> setCallerIdentifier(Object identifier);
	Object getCallerIdentifier();
	
	Function<INPUT,OUTPUT> setExecutionPhaseTry(FunctionExecutionPhaseTry executionPhaseTry);
	FunctionExecutionPhaseTry getExecutionPhaseTry();
	FunctionExecutionPhaseTry getExecutionPhaseTry(Boolean injectIfNull);
	FunctionExecutionPhaseTry try_();
	Function<INPUT,OUTPUT> addTryBeginRunnables(Collection<Runnable> runnables);
	Function<INPUT,OUTPUT> addTryBeginRunnables(Runnable...runnables);
	Function<INPUT,OUTPUT> addTryRunRunnables(Collection<Runnable> runnables);
	Function<INPUT,OUTPUT> addTryRunRunnables(Runnable...runnables);
	Function<INPUT,OUTPUT> addTryEndRunnables(Collection<Runnable> runnables);
	Function<INPUT,OUTPUT> addTryEndRunnables(Runnable...runnables);
	
	Function<INPUT,OUTPUT> setExecutionPhaseCatch(FunctionExecutionPhaseCatch executionPhaseCatch);
	FunctionExecutionPhaseCatch getExecutionPhaseCatch();
	FunctionExecutionPhaseCatch getExecutionPhaseCatch(Boolean injectIfNull);
	FunctionExecutionPhaseCatch catch_();
	
	Function<INPUT,OUTPUT> setExecutionPhaseFinally(FunctionExecutionPhaseFinally executionPhaseFinally);
	FunctionExecutionPhaseFinally getExecutionPhaseFinally();
	FunctionExecutionPhaseFinally getExecutionPhaseFinally(Boolean injectIfNull);
	FunctionExecutionPhaseFinally finally_();
	
	Function<INPUT,OUTPUT> setIsNotifyAfterExecutionPhaseFinally(Boolean isNotifyAfterExecutionPhaseFinally);
	Boolean getIsNotifyAfterExecutionPhaseFinally();
	
	Function<INPUT,OUTPUT> setIsNotifyOnThrowableIsNull(Boolean isNotifyOnThrowableIsNull);
	Boolean getIsNotifyOnThrowableIsNull();
	
	Function<INPUT,OUTPUT> setIsNotifyOnThrowableIsNotNull(Boolean isNotifyOnThrowableIsNotNull);
	Boolean getIsNotifyOnThrowableIsNotNull();
	
	Function<INPUT,OUTPUT> setIsExecuteAsynchronously(Boolean isExecuteAsynchronously);
	Boolean getIsExecuteAsynchronously();
	
	@Override Function<INPUT,OUTPUT> copyProperty(Object key, Properties properties);
	
	@Override Function<INPUT,OUTPUT> setProperty(Object key, Object value);
	
	Boolean getIsDoTry();
	Function<INPUT,OUTPUT> setIsDoTry(Boolean isDoTry);
	
	Boolean getIsDoCatch();
	Function<INPUT,OUTPUT> setIsDoCatch(Boolean isDoCatch);
	
	Boolean getIsDoFinally();
	Function<INPUT,OUTPUT> setIsDoFinally(Boolean isDoFinally);
	
	Boolean getIsDoMonitoring();
	Function<INPUT,OUTPUT> setIsDoMonitoring(Boolean isDoMonitoring);
}
