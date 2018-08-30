package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionExecutionPhaseMomentImpl extends AbstractObject implements FunctionExecutionPhaseMoment,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FunctionExecutionPhaseMoment run() {
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
	public Collection<Runnable> getRunnables(){
		return (Collection<Runnable>) getProperties().getRunnables();
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
		}
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMoment addFunctionRunnables(FunctionRunnable<?>...functionRunnables){
		if(functionRunnables!=null && functionRunnables.length>0)
			addFunctionRunnables(Arrays.asList(functionRunnables));
		return this;
	}
	
}
