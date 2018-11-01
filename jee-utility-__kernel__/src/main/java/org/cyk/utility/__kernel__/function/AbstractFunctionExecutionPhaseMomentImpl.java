package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.assertion.AssertionBuilder;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionExecutionPhaseMomentImpl extends AbstractObject implements FunctionExecutionPhaseMoment,Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<AssertionBuilder> assertionBuilders;
	private Boolean runned;
	private Function<?,Collection<Assertion>> assertionsProvider;
	
	@Override
	public Function<?,Collection<Assertion>> getAssertionsProvider() {
		return assertionsProvider;
	}
	
	@Override
	public FunctionExecutionPhaseMoment setAssertionsProvider(Function<?,Collection<Assertion>> assertionsProvider) {
		this.assertionsProvider = assertionsProvider;
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment run() {
		setRunned(Boolean.TRUE);
		
		Collection<Assertion> assertions = new ArrayList<>();
		
		Function<?,Collection<Assertion>> assertionsProvider = getAssertionsProvider();
		if(assertionsProvider!=null)
			__injectKernelHelper__().addToCollection(assertions, assertionsProvider.execute().getOutput());
			
		Collection<Assertion> __assertions__ = getAssertions();	
		if(__assertions__!=null) {
			for(Assertion index : __assertions__) {
				if(assertions == null)
					assertions = new ArrayList<>();
				assertions.add(index);
			}
		}
		
		Collection<AssertionBuilder> assertionBuilders = getAssertionBuilders();	
		if(assertionBuilders!=null){
			for(AssertionBuilder index : assertionBuilders){
				if(assertions == null)
					assertions = new ArrayList<>();
				assertions.add(index.execute());
			}
		}
		
		if(assertions!=null){
			for(Assertion index : assertions){
				if(Boolean.FALSE.equals(index.getValue()))
					throw new RuntimeException(index.getMessageWhenValueIsNotTrue());
			}
		}
		
		Collection<Runnable> runnables = getRunnables();
		if(runnables!=null){
			for(Runnable index : runnables){
				index.run();
			}
		}
		
		Collection<FunctionRunnable<?>> functionRunnables = getFunctionRunnables();
		if(functionRunnables!=null){
			for(FunctionRunnable<?> index : functionRunnables){
				index.getRunnable().run();
			}
		}
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Assertion> getAssertions(){
		return (Collection<Assertion>) getProperties().getAssertions();
	}
	
	@Override
	public FunctionExecutionPhaseMoment setAssertions(Collection<Assertion> assertions){
		getProperties().setAssertions(assertions);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addAssertions(Collection<Assertion> assertions){
		if(assertions!=null && !assertions.isEmpty()) {
			Collection<Assertion> collection = getAssertions();
			if(collection == null)
				setAssertions(collection = new ArrayList<>());
			collection.addAll(assertions);
		}
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addAssertions(Assertion...assertions){
		if(assertions!=null && assertions.length>0)
			addAssertions(Arrays.asList(assertions));
		return this;
	}
	
	@Override
	public Collection<AssertionBuilder> getAssertionBuilders(){
		return assertionBuilders;
	}
	
	@Override
	public FunctionExecutionPhaseMoment setAssertionBuilders(Collection<AssertionBuilder> assertionBuilders){
		this.assertionBuilders = assertionBuilders;
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addAssertionBuilders(Collection<AssertionBuilder> assertionBuilders){
		if(assertionBuilders!=null && !assertionBuilders.isEmpty()) {
			Collection<AssertionBuilder> collection = getAssertionBuilders();
			if(collection == null)
				setAssertionBuilders(collection = new ArrayList<>());
			collection.addAll(assertionBuilders);
		}
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addAssertionBuilders(AssertionBuilder...assertionBuilders){
		if(assertionBuilders!=null && assertionBuilders.length>0)
			addAssertionBuilders(Arrays.asList(assertionBuilders));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Runnable> getRunnables(){
		return (Collection<Runnable>) getProperties().getRunnables();
	}
	
	@Override
	public Collection<Runnable> getRunnables(Boolean instanciateIfNull) {
		Collection<Runnable> runnables = getRunnables();
		if(runnables == null && Boolean.TRUE.equals(instanciateIfNull))
			setRunnables(runnables = new ArrayList<>());
		return runnables;
	}
	
	@Override
	public FunctionExecutionPhaseMoment setRunnables(Collection<Runnable> runnables){
		getProperties().setRunnables(runnables);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addRunnables(Collection<Runnable> runnables){
		if(runnables!=null && !runnables.isEmpty()) {
			Collection<Runnable> collection = getRunnables();
			if(collection == null)
				setRunnables(collection = new ArrayList<>());
			collection.addAll(runnables);
		}
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addRunnables(Runnable...runnables){
		if(runnables!=null && runnables.length>0)
			addRunnables(Arrays.asList(runnables));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<FunctionRunnable<?>> getFunctionRunnables(){
		return (Collection<FunctionRunnable<?>>) getProperties().getFromPath(Properties.FUNCTION,Properties.RUNNABLES);
	}
	
	@Override
	public FunctionExecutionPhaseMoment setFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables){
		getProperties().setFromPath(new Object[] {Properties.FUNCTION,Properties.RUNNABLES}, functionRunnables);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables){
		if(functionRunnables!=null && !functionRunnables.isEmpty()) {
			Collection<FunctionRunnable<?>> collection = getFunctionRunnables();
			if(collection == null)
				setFunctionRunnables(collection = new ArrayList<>());
			collection.addAll(functionRunnables);
			
			for(FunctionRunnable<?> index : functionRunnables)
				if(index.getFunction() == null)
					try {
						//TODO must be integrated in FieldValueSetter
						MethodUtils.invokeMethod(index, "setFunction", getParent().getParent());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
		}
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addFunctionRunnables(FunctionRunnable<?>...functionRunnables){
		if(functionRunnables!=null && functionRunnables.length>0)
			addFunctionRunnables(Arrays.asList(functionRunnables));
		return this;
	}
	
	@Override
	public Boolean getRunned() {
		return runned;
	}
	
	@Override
	public FunctionExecutionPhaseMoment setRunned(Boolean runned) {
		this.runned = runned;
		return this;
	}
	
	@Override
	public FunctionExecutionPhase getParent() {
		return (FunctionExecutionPhase) super.getParent();
	}
}
