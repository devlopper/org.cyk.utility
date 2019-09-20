package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.__kernel__.ClassHelper;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends AbstractObject implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	private FunctionExecutionPhaseTry executionPhaseTry;
	private FunctionExecutionPhaseCatch executionPhaseCatch;
	private FunctionExecutionPhaseFinally executionPhaseFinally;
	private Function<?, Collection<Assertion>> preConditionsAssertionsProvider;
	private Function<?, Collection<Assertion>> postConditionsAssertionsProvider;
	private Boolean isNotifyOnThrowableIsNull;
	private Boolean isNotifyOnThrowableIsNotNull;
	private Boolean isNotifyAfterExecutionPhaseFinally;
	private Boolean isExecuteAsynchronously,isDoTry,isDoCatch,isDoFinally,isDoMonitoring;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsExecutable(Boolean.TRUE);
	}
	
	protected void __executeCode__() {
		Boolean executable = __executeGetIsExecutable__(getIsExecutable());
		if(Boolean.TRUE.equals(executable)){
			Boolean isDoMonitoring = getIsDoMonitoring();
			Long start = null;
			if(isDoMonitoring == null || isDoMonitoring) {
				start = System.currentTimeMillis();
				getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.START}, start);	
			}
			try {
				Boolean isDoTry = getIsDoTry();
				if(isDoTry == null || isDoTry) {
					__try__();
				}
			} catch (Exception exception) {
				Boolean isDoCatch = getIsDoCatch();
				if(isDoCatch == null || isDoCatch) {
					__catch__(exception);	
				}
			} finally {
				Boolean isDoFinally = getIsDoFinally();
				if(isDoFinally == null || isDoFinally) {
					__finally__();
					
					Long end = System.currentTimeMillis();
					getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.END}, end);
					getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION}, end - start);
					
					__notifyAfterExecutionPhaseFinally__();
					
					__log__();
				}
			}
		}else {
			//throw new RuntimeException(getClass()+" is not executable.");
			System.err.println(getClass()+" is not executable.");
			//TODO log warning not executable
		}
	}
	
	@Override
	public Function<INPUT,OUTPUT> execute() {
		Boolean isExecuteAsynchronously = getIsExecuteAsynchronously();
		if(Boolean.TRUE.equals(isExecuteAsynchronously)) {
			Thread thread = new Thread(
					new Runnable() {
						@Override
						public void run() {
							__executeCode__();
						}
					}
				);
			getProperties().setThread(thread);
			thread.start();
		}else {
			__executeCode__();	
		}
		
		return this;
	}
	
	@Override
	public void executeToReturnVoid() {
		execute();
	}
	
	@Override
	public Function<INPUT, OUTPUT> executeWithOneParameter(Object parameter1) {
		getProperties().setParameter(parameter1);
		return execute();
	}
	
	@Override
	public void executeWithOneParameterToReturnVoid(Object parameter1) {
		executeWithOneParameter(parameter1);
	}
	
	protected void __try__() throws Exception {
		FunctionExecutionPhaseTry executionPhaseTry = getExecutionPhaseTry();		
		
		if((executionPhaseTry == null || executionPhaseTry.getBegin()==null || executionPhaseTry.getBegin().getAssertionsProvider()==null) && getPreConditionsAssertionsProvider()!=null)
			(executionPhaseTry = try_()).begin().setAssertionsProvider(getPreConditionsAssertionsProvider());
		
		if((executionPhaseTry == null || executionPhaseTry.getEnd()==null || executionPhaseTry.getEnd().getAssertionsProvider()==null) && getPostConditionsAssertionsProvider()!=null)
			(executionPhaseTry = try_()).end().setAssertionsProvider(getPostConditionsAssertionsProvider());
		
		__executePhaseMoment__(executionPhaseTry, FunctionExecutionPhaseMomentBegin.class);		
		__executePhaseMoment__(executionPhaseTry, FunctionExecutionPhaseMomentRun.class);
		
		Boolean isCodeFromFunctionExecutable = executionPhaseTry == null || executionPhaseTry.getIsCodeFromFunctionExecutable() == null 
				|| Boolean.TRUE.equals(executionPhaseTry.getIsCodeFromFunctionExecutable());
		
		if(Boolean.TRUE.equals(isCodeFromFunctionExecutable)) {
			OUTPUT output = null;
			if(Boolean.TRUE.equals(isCodeFromFunctionExecutable))
				output = _execute_();	
			getProperties().setOutput(output);	
		}
		
		__executePhaseMoment__(executionPhaseTry, FunctionExecutionPhaseMomentEnd.class);
	}
	
	protected void __catch__(Exception exception) {
		__executePhaseMoment__(getExecutionPhaseTry(), FunctionExecutionPhaseMomentBegin.class);
		if(Boolean.TRUE.equals(getIsCatchThrowable())) {
			//TODO print the className.methodName.lineNumber. Those values can be taken from the stack trace elements array
			System.err.println(getClass()+" : Caught exception : "+exception);
			//exception.printStackTrace();
			getProperties().setThrowable(exception);
			__executePhaseMoment__(getExecutionPhaseCatch(), FunctionExecutionPhaseMomentEnd.class);
		}else {
			RuntimeException runtimeException;
			if(exception instanceof RuntimeException)
				runtimeException =  (RuntimeException) exception;
			else
				runtimeException = new RuntimeException(exception);
			__executePhaseMoment__(getExecutionPhaseCatch(), FunctionExecutionPhaseMomentEnd.class);
			throw runtimeException;
		}
		
	}

	protected void __finally__() {
		__executePhaseMoment__(getExecutionPhaseFinally(), FunctionExecutionPhaseMomentBegin.class);
		
		Collection<Runnable> runnables = getFinallyRunnablesInTryCatchFinally();
		if(runnables!=null && !runnables.isEmpty()) {
			for(Runnable index : runnables)
				index.run();
		}
		
		__executePhaseMoment__(getExecutionPhaseFinally(), FunctionExecutionPhaseMomentEnd.class);
	}
	
	protected final OUTPUT _execute_() throws Exception {
		OUTPUT output = null;
		Class<? extends FunctionRunnable<?>> functionRunnableClass = DependencyInjection.inject(FunctionRunnableMap.class).get(getClass());	
		if(functionRunnableClass == null)
			output = __execute__();
		else
			output = __execute__(functionRunnableClass);
		return output;
	}
	
	protected OUTPUT __execute__() throws Exception {
		throw new RuntimeException(getClass()+" : Implementation or runnable required");
	}
	
	@SuppressWarnings("unchecked")
	protected OUTPUT __execute__(Class<? extends FunctionRunnable<?>> functionRunnableClass) throws Exception {
		@SuppressWarnings("rawtypes")
		FunctionRunnable functionRunnable = ClassHelper.instanciate(functionRunnableClass);
		functionRunnable.setFunction(this);
		Runnable runnable = functionRunnable.getRunnable();
		if(runnable == null) {
			throw new RuntimeException(getClass()+" : Function runnable implementation required");
		}else {
			runnable.run();
			return (OUTPUT) functionRunnable.getOutput();	
		}
	}
	
	protected Boolean __executeGetIsExecutable__(Boolean value){
		return value;
	}
	
	protected void __log__(){}
	
	@Deprecated protected void __executeVerifyPreConditions__(){}
	@Deprecated protected void __executeVerifyPostConditions__(){}
	@Deprecated protected void __beforeExecute__(){}
	@Deprecated protected void __afterExecute__(){}
	@Deprecated protected void afterExecute(){}
	
	protected void __executePhaseMoment__(FunctionExecutionPhase executionPhase,Class<? extends FunctionExecutionPhaseMoment> momentClass) {
		if(executionPhase!=null) {
			FunctionExecutionPhaseMoment moment = null;
			if(FunctionExecutionPhaseMomentBegin.class.equals(momentClass))
				moment = executionPhase.getBegin();
			else if(FunctionExecutionPhaseMomentRun.class.equals(momentClass))
				moment = executionPhase.getRun();
			else if(FunctionExecutionPhaseMomentEnd.class.equals(momentClass))
				moment = executionPhase.getEnd();
			
			if(moment!=null)
				moment.run();
		}
	}
	
	@Override
	public Function<INPUT, OUTPUT> setInput(INPUT input) {
		getProperties().setInput(input);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OUTPUT getOutput() {
		return (OUTPUT) getProperties().getOutput();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends OUTPUT> T getOutputAs(Class<T> aClass) {
		return (T) getOutput();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT,OUTPUT> setParent(Object parent) {
		return (Function<INPUT, OUTPUT>) super.setParent(parent);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT,OUTPUT> setProperties(Properties properties) {
		return (Function<INPUT,OUTPUT>) super.setProperties(properties);
	}
	
	@Override
	public Boolean getIsExecutable() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.EXECUTABLE);
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsExecutable(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.EXECUTABLE}, value);
		return this;
	}
	
	@Override
	public Boolean getIsCatchThrowable() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.CATCH,Properties.THROWABLE);
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsCatchThrowable(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.CATCH,Properties.THROWABLE}, value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT, OUTPUT> addChild(Object... child) {
		return (Function<INPUT, OUTPUT>) super.addChild(child);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT, OUTPUT> addChildren(Collection<Object> children) {
		return (Function<INPUT, OUTPUT>) super.addChildren(children);
	}
	
	@Override
	public Runnable getRunnable() {
		return (Runnable) getProperties().getRunnable();
	}
	
	@Override
	public Function<INPUT, OUTPUT> setRunnable(Runnable runnable) {
		getProperties().setRunnable(runnable);
		return this;
	}
	
	@Override
	public Class<?> getCallerClass() {
		return (Class<?>) getProperties().getFromPath(Properties.CALLER,Properties.CLASS);
	}
	
	@Override
	public Function<INPUT, OUTPUT> setCallerClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[] {Properties.CALLER,Properties.CLASS}, aClass);
		return this;
	}
	
	@Override
	public Class<?> getCallerIdentifier() {
		return (Class<?>) getProperties().getFromPath(Properties.CALLER,Properties.IDENTIFIER);
	}
	
	@Override
	public Function<INPUT, OUTPUT> setCallerIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[] {Properties.CALLER,Properties.IDENTIFIER}, identifier);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseTry getExecutionPhaseTry() {
		return executionPhaseTry;
	}
	
	@Override
	public FunctionExecutionPhaseTry getExecutionPhaseTry(Boolean injectIfNull) {
		FunctionExecutionPhaseTry executionPhaseTry = getExecutionPhaseTry();
		if(executionPhaseTry == null && Boolean.TRUE.equals(injectIfNull))
			setExecutionPhaseTry(executionPhaseTry = __inject__(FunctionExecutionPhaseTry.class));
		return executionPhaseTry;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setExecutionPhaseTry(FunctionExecutionPhaseTry executionPhaseTry) {
		this.executionPhaseTry = executionPhaseTry;
		if(this.executionPhaseTry!=null)
			this.executionPhaseTry.setParent(this);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseCatch getExecutionPhaseCatch() {
		return executionPhaseCatch;
	}
	
	@Override
	public FunctionExecutionPhaseTry try_() {
		return getExecutionPhaseTry(Boolean.TRUE);
	}
	
	@Override
	public Function<INPUT, OUTPUT> addTryBeginRunnables(Collection<Runnable> runnables) {
		if(runnables!=null && !runnables.isEmpty())
			try_().getBegin(Boolean.TRUE).getRunnables(Boolean.TRUE).addAll(runnables);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addTryBeginRunnables(Runnable... runnables) {
		if(runnables!=null && runnables.length>0)
			addTryBeginRunnables(Arrays.asList(runnables));
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addTryRunRunnables(Collection<Runnable> runnables) {
		if(runnables!=null && !runnables.isEmpty())
			try_().getRun(Boolean.TRUE).getRunnables(Boolean.TRUE).addAll(runnables);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addTryRunRunnables(Runnable... runnables) {
		if(runnables!=null && runnables.length>0)
			addTryRunRunnables(Arrays.asList(runnables));
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addTryEndRunnables(Collection<Runnable> runnables) {
		if(runnables!=null && !runnables.isEmpty())
			try_().getEnd(Boolean.TRUE).getRunnables(Boolean.TRUE).addAll(runnables);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addTryEndRunnables(Runnable... runnables) {
		if(runnables!=null && runnables.length>0)
			addTryEndRunnables(Arrays.asList(runnables));
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseCatch getExecutionPhaseCatch(Boolean injectIfNull) {
		FunctionExecutionPhaseCatch executionPhaseCatch = getExecutionPhaseCatch();
		if(executionPhaseCatch == null && Boolean.TRUE.equals(injectIfNull))
			setExecutionPhaseCatch(executionPhaseCatch = __inject__(FunctionExecutionPhaseCatch.class));
		return executionPhaseCatch;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setExecutionPhaseCatch(FunctionExecutionPhaseCatch executionPhaseCatch) {
		this.executionPhaseCatch = executionPhaseCatch;
		if(this.executionPhaseCatch!=null)
			this.executionPhaseCatch.setParent(this);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseCatch catch_() {
		return getExecutionPhaseCatch(Boolean.TRUE);
	}
	
	@Override
	public FunctionExecutionPhaseFinally getExecutionPhaseFinally() {
		return executionPhaseFinally;
	}
	
	@Override 
	public FunctionExecutionPhaseFinally getExecutionPhaseFinally(Boolean injectIfNull) {
		FunctionExecutionPhaseFinally executionPhaseFinally = getExecutionPhaseFinally();
		if(executionPhaseFinally == null && Boolean.TRUE.equals(injectIfNull))
			setExecutionPhaseFinally(executionPhaseFinally = __inject__(FunctionExecutionPhaseFinally.class));
		return executionPhaseFinally;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setExecutionPhaseFinally(FunctionExecutionPhaseFinally executionPhaseFinally) {
		this.executionPhaseFinally = executionPhaseFinally;
		if(this.executionPhaseFinally!=null)
			this.executionPhaseFinally.setParent(this);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseFinally finally_() {
		return getExecutionPhaseFinally(Boolean.TRUE);
	}
	
	@Override
	public Function<?, Collection<Assertion>> getPreConditionsAssertionsProvider() {
		return preConditionsAssertionsProvider;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setPreConditionsAssertionsProvider(Function<?, Collection<Assertion>> assertionsProvider) {
		this.preConditionsAssertionsProvider = assertionsProvider;
		return this;
	}
	
	@Override
	public Function<?, Collection<Assertion>> getPostConditionsAssertionsProvider() {
		return postConditionsAssertionsProvider;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setPostConditionsAssertionsProvider(Function<?, Collection<Assertion>> assertionsProvider) {
		this.postConditionsAssertionsProvider = assertionsProvider;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override @Deprecated
	public Collection<Runnable> getFinallyRunnablesInTryCatchFinally(){
		return (Collection<Runnable>) getProperties().getFromPath(Properties.TRY_CATCH_FINALLY,Properties.FINALLY,Properties.RUNNABLES);
	}
	
	@Override @Deprecated
	public Function<INPUT, OUTPUT> setFinallyRunnablesInTryCatchFinally(Collection<Runnable> runnables){
		getProperties().setFromPath(new Object[] {Properties.TRY_CATCH_FINALLY,Properties.FINALLY,Properties.RUNNABLES}, runnables);
		return this;
	}
	
	@Override @Deprecated
	public Function<INPUT, OUTPUT> addFinallyRunnablesInTryCatchFinally(Collection<Runnable> runnables){
		if(runnables!=null && !runnables.isEmpty()) {
			Collection<Runnable> collection = getFinallyRunnablesInTryCatchFinally();
			if(collection == null)
				setFinallyRunnablesInTryCatchFinally(collection = new ArrayList<>());	
			collection.addAll(runnables);
		}
		return this;
	}
	
	@Override @Deprecated
	public Function<INPUT, OUTPUT> addFinallyRunnablesInTryCatchFinally(Runnable...runnables){
		if(runnables!=null && runnables.length>0) {
			addFinallyRunnablesInTryCatchFinally(Arrays.asList(runnables));
		}
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsNotifyAfterExecutionPhaseFinally(Boolean isNotifyAfterExecutionPhaseFinally) {
		this.isNotifyAfterExecutionPhaseFinally = isNotifyAfterExecutionPhaseFinally;
		return this;
	}
	
	@Override
	public Boolean getIsNotifyAfterExecutionPhaseFinally() {
		return isNotifyAfterExecutionPhaseFinally;
	}
	
	protected void __notifyAfterExecutionPhaseFinally__() {
		Throwable throwable = (Throwable) getProperties().getThrowable();
		if(throwable == null) {
			if(Boolean.TRUE.equals(getIsNotifyOnThrowableIsNull()))
				__notifyOnThrowableIsNull__();
		}else {
			if(Boolean.TRUE.equals(getIsNotifyOnThrowableIsNotNull()))
				__notifyOnThrowableIsNotNull__(throwable);
		}
	}
	
	@Override
	public Boolean getIsNotifyOnThrowableIsNull() {
		return isNotifyOnThrowableIsNull;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsNotifyOnThrowableIsNull(Boolean isNotifyOnThrowableIsNull) {
		this.isNotifyOnThrowableIsNull = isNotifyOnThrowableIsNull;
		return this;
	}
	
	protected void __notifyOnThrowableIsNull__() {
		System.out.println("Success.");
	}
	
	@Override
	public Boolean getIsNotifyOnThrowableIsNotNull() {
		return isNotifyOnThrowableIsNotNull;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsNotifyOnThrowableIsNotNull(Boolean isNotifyOnThrowableIsNotNull) {
		this.isNotifyOnThrowableIsNotNull = isNotifyOnThrowableIsNotNull;
		return this;
	}
	
	@Override
	public Boolean getIsExecuteAsynchronously() {
		return isExecuteAsynchronously;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsExecuteAsynchronously(Boolean isExecuteAsynchronously) {
		this.isExecuteAsynchronously = isExecuteAsynchronously;
		return this;
	}
	
	@Override
	public Boolean getIsDoCatch() {
		return isDoCatch;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsDoCatch(Boolean isDoCatch) {
		this.isDoCatch = isDoCatch;
		return this;
	}
	
	@Override
	public Boolean getIsDoFinally() {
		return isDoFinally;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsDoFinally(Boolean isDoFinally) {
		this.isDoFinally = isDoFinally;
		return this;
	}
	
	@Override
	public Boolean getIsDoTry() {
		return isDoTry;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsDoTry(Boolean isDoTry) {
		this.isDoTry = isDoTry;
		return this;
	}
	
	@Override
	public Boolean getIsDoMonitoring() {
		return isDoMonitoring;
	}
	
	@Override
	public Function<INPUT, OUTPUT> setIsDoMonitoring(Boolean isDoMonitoring) {
		this.isDoMonitoring = isDoMonitoring;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT, OUTPUT> setProperty(Object key, Object value) {
		return (Function<INPUT, OUTPUT>) super.setProperty(key, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Function<INPUT, OUTPUT> copyProperty(Object key, Properties properties) {
		return (Function<INPUT, OUTPUT>) super.copyProperty(key, properties);
	}
	
	protected void __notifyOnThrowableIsNotNull__(Throwable throwable) {
		System.out.println("Error : "+throwable);
	}
	
}
