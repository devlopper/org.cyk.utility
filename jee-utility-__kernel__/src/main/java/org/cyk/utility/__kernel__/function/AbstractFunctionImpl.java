package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.KernelHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionImpl<INPUT,OUTPUT> extends AbstractObject implements Function<INPUT,OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsExecutable(Boolean.TRUE);
	}
	
	@Override
	public Function<INPUT,OUTPUT> execute() {
		Boolean executable = __executeGetIsExecutable__(getIsExecutable());
		if(Boolean.TRUE.equals(executable)){
			Long start = System.currentTimeMillis();
			OUTPUT output = null;
			try {
				__executeVerifyPreConditions__();
				getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.START}, start);
				
				Runnable runnable = (Runnable) getProperties().getRunnable();
				__beforeExecute__();
				
				if(runnable == null){
					executable = (Boolean) getProperties().getFromPath(Properties.IS,Properties.CORE,Properties.EXECUTABLE);
					if(executable == null)
						executable =  Boolean.TRUE;
					if(Boolean.TRUE.equals(executable))
						output = _execute_();
				}else {
					runnable.run();
				}
				
				__afterExecute__();
				__executeVerifyPostConditions__();
			} catch (Exception exception) {
				if(Boolean.TRUE.equals(getIsCatchThrowable())) {
					//TODO print the className.methodName.lineNumber. Those values can be taken from the stack trace elements array
					System.err.println(getClass()+" : Caught exception : "+exception);
					getProperties().setThrowable(exception);	
				}else {
					RuntimeException runtimeException;
					if(exception instanceof RuntimeException)
						runtimeException =  (RuntimeException) exception;
					else
						runtimeException = new RuntimeException(exception);
					throw runtimeException;
				}
			} finally {
				__finally__();
			}
			
			Long end = System.currentTimeMillis();
			getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.END}, end);
			getProperties().setFromPath(new Object[]{Properties.FUNCTION,Properties.EXECUTION,Properties.DURATION}, end - start);
			getProperties().setOutput(output);
			afterExecute();	
			
		}else {
			//throw new RuntimeException(getClass()+" is not executable.");
			System.err.println(getClass()+" is not executable.");
			//TODO log warning not executable
		}
		return this;
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
		FunctionRunnable functionRunnable = DependencyInjection.inject(KernelHelper.class).instanciate(functionRunnableClass);
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
	
	protected void __executeVerifyPreConditions__(){}
	protected void __executeVerifyPostConditions__(){}
	
	protected void __beforeExecute__(){}

	protected void __afterExecute__(){}
	
	protected void afterExecute(){}
	
	protected void __finally__() {
		Collection<Runnable> runnables = getFinallyRunnablesInTryCatchFinally();
		if(runnables!=null && !runnables.isEmpty()) {
			for(Runnable index : runnables)
				index.run();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Runnable> getFinallyRunnablesInTryCatchFinally(){
		return (Collection<Runnable>) getProperties().getFromPath(Properties.TRY_CATCH_FINALLY,Properties.FINALLY,Properties.RUNNABLES);
	}
	
	@Override
	public Function<INPUT, OUTPUT> setFinallyRunnablesInTryCatchFinally(Collection<Runnable> runnables){
		getProperties().setFromPath(new Object[] {Properties.TRY_CATCH_FINALLY,Properties.FINALLY,Properties.RUNNABLES}, runnables);
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addFinallyRunnablesInTryCatchFinally(Collection<Runnable> runnables){
		if(runnables!=null && !runnables.isEmpty()) {
			Collection<Runnable> collection = getFinallyRunnablesInTryCatchFinally();
			if(collection == null)
				setFinallyRunnablesInTryCatchFinally(collection = new ArrayList<>());	
			collection.addAll(runnables);
		}
		return this;
	}
	
	@Override
	public Function<INPUT, OUTPUT> addFinallyRunnablesInTryCatchFinally(Runnable...runnables){
		if(runnables!=null && runnables.length>0) {
			addFinallyRunnablesInTryCatchFinally(Arrays.asList(runnables));
		}
		return this;
	}
}
