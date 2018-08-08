package org.cyk.utility.function;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.Assertion;
import org.cyk.utility.assertion.AssertionBuilder;
import org.cyk.utility.collection.CollectionHelper;

public class ExecutionPhase extends org.cyk.utility.__kernel__.function.ExecutionPhase implements Serializable {
	private static final long serialVersionUID = 1L;

	public Collection<Assertion> getAssertions(){
		return (Collection<Assertion>) getProperties().getFromPath(Properties.ASSERTIONS,Properties.__THIS__);
	}
	
	public ExecutionPhase setAssertions(Collection<Assertion> assertions){
		getProperties().setFromPath(new Object[]{Properties.ASSERTIONS,Properties.__THIS__}, assertions);
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
	
	/**/
	
	public Collection<AssertionBuilder> getAssertionBuilders(){
		return (Collection<AssertionBuilder>) getProperties().getFromPath(Properties.ASSERTIONS,Properties.BUILDER);
	}
	
	public ExecutionPhase setAssertionBuilders(Collection<AssertionBuilder> assertionBuilders){
		getProperties().setFromPath(new Object[]{Properties.ASSERTIONS,Properties.BUILDER}, assertionBuilders);
		return this;
	}
	
	public ExecutionPhase addAssertionBuilders(Collection<AssertionBuilder> assertionBuilders){
		setAssertionBuilders(__inject__(CollectionHelper.class).add(getAssertionBuilders(), Boolean.TRUE, assertionBuilders));
		return this;
	}
	
	public ExecutionPhase addAssertionBuilders(AssertionBuilder...assertionBuilders){
		addAssertionBuilders(__inject__(CollectionHelper.class).instanciate(assertionBuilders));
		return this;
	}
	
	/**/
	
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