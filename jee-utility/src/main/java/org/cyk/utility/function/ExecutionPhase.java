package org.cyk.utility.function;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.assertion.Assertion;
import org.cyk.utility.collection.CollectionHelper;

public class ExecutionPhase extends org.cyk.utility.__kernel__.function.ExecutionPhase implements Serializable {
	private static final long serialVersionUID = 1L;

	public Collection<Assertion> getAssertions(){
		return (Collection<Assertion>) getProperties().getAssertions();
	}
	
	public ExecutionPhase setAssertions(Collection<Assertion> assertions){
		getProperties().setAssertions(assertions);
		return this;
	}
	
	public ExecutionPhase addAssertions(Collection<Assertion> assertions){
		setAssertions(__inject__(CollectionHelper.class).add(getAssertions(), Boolean.TRUE, assertions));
		return this;
	}
	
	public ExecutionPhase addAssertions(Assertion...assertions){
		addAssertions(__inject__(CollectionHelper.class).instanciate(assertions));
		return this;
	}
	
	public Collection<Runnable> getRunnables(){
		return (Collection<Runnable>) getProperties().getRunnables();
	}
	
	public ExecutionPhase setRunnables(Collection<Runnable> runnables){
		getProperties().setRunnables(runnables);
		return this;
	}
	
	public ExecutionPhase addRunnables(Collection<Runnable> runnables){
		setRunnables(__inject__(CollectionHelper.class).add(getRunnables(), Boolean.TRUE, runnables));
		return this;
	}
	
	public ExecutionPhase addRunnables(Runnable...runnables){
		addRunnables(__inject__(CollectionHelper.class).instanciate(runnables));
		return this;
	}
	
}
