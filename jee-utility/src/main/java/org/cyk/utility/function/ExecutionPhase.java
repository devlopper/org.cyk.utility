package org.cyk.utility.function;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.function.FunctionRunnable;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionBuilder;

@Deprecated
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
		setAssertions(CollectionHelper.add(getAssertions(), Boolean.TRUE, assertions));
		return this;
	}
	
	public ExecutionPhase addAssertions(Assertion...assertions){
		addAssertions(List.of(assertions));
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
		setAssertionBuilders(CollectionHelper.add(getAssertionBuilders(), Boolean.TRUE, assertionBuilders));
		return this;
	}
	
	public ExecutionPhase addAssertionBuilders(AssertionBuilder...assertionBuilders){
		addAssertionBuilders(List.of(assertionBuilders));
		return this;
	}
	
	/**/
	
	@Deprecated
	public Collection<Runnable> getRunnables(){
		return (Collection<Runnable>) getProperties().getRunnables();
	}
	
	@Deprecated
	public ExecutionPhase setRunnables(Collection<Runnable> runnables){
		getProperties().setRunnables(runnables);
		return this;
	}
	
	@Deprecated
	public ExecutionPhase addRunnables(Collection<Runnable> runnables){
		setRunnables(CollectionHelper.add(getRunnables(), Boolean.TRUE, runnables));
		return this;
	}
	
	@Deprecated
	public ExecutionPhase addRunnables(Runnable...runnables){
		addRunnables(List.of(runnables));
		return this;
	}
	
	@Deprecated
	public Collection<FunctionRunnable<?>> getFunctionRunnables(){
		return (Collection<FunctionRunnable<?>>) getProperties().getFromPath(Properties.FUNCTION,Properties.RUNNABLES);
	}
	
	@Deprecated
	public ExecutionPhase setFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables){
		getProperties().setFromPath(new Object[] {Properties.FUNCTION,Properties.RUNNABLES}, functionRunnables);
		return this;
	}
	
	@Deprecated
	public ExecutionPhase addFunctionRunnables(Collection<FunctionRunnable<?>> functionRunnables){
		setFunctionRunnables(CollectionHelper.add(getFunctionRunnables(), Boolean.TRUE, functionRunnables));
		return this;
	}
	
	@Deprecated
	public ExecutionPhase addFunctionRunnables(FunctionRunnable<?>...functionRunnables){
		addFunctionRunnables(List.of(functionRunnables));
		return this;
	}
	
	/* Try finally */
	
	@Deprecated
	public Collection<Runnable> getFinallyRunnables(){
		return (Collection<Runnable>) getProperties().getFromPath(Properties.FINALLY,Properties.RUNNABLES);
	}
	
	@Deprecated
	public ExecutionPhase setFinallyRunnables(Collection<Runnable> runnables){
		getProperties().setFromPath(new Object[] {Properties.FINALLY,Properties.RUNNABLES}, runnables);
		return this;
	}
	
	@Deprecated
	public ExecutionPhase addFinallyRunnables(Collection<Runnable> runnables){
		setFinallyRunnables(CollectionHelper.add(getFinallyRunnables(), Boolean.TRUE, runnables));
		return this;
	}
	
	@Deprecated
	public ExecutionPhase addFinallyRunnables(Runnable...runnables){
		addFinallyRunnables(List.of(runnables));
		return this;
	}
	
}
