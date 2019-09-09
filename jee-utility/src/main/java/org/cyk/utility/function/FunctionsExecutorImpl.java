package org.cyk.utility.function;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.runnable.RunnablesExecutor;

@Dependent @Deprecated
public class FunctionsExecutorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements FunctionsExecutor,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Functions functions;
	
	@Override
	public Function<Properties, Void> execute() {
		Functions functions = getFunctions();
		if(CollectionHelperImpl.__isNotEmpty__(functions)) {
			RunnablesExecutor runnablesExecutor = __inject__(RunnablesExecutor.class);
			runnablesExecutor.getExecutorServiceBuilder(Boolean.TRUE).setCorePoolSize(CollectionHelperImpl.__getSize__(functions)/2+1);
			runnablesExecutor.getExecutorServiceBuilder(Boolean.TRUE).setMaximumPoolSize(CollectionHelperImpl.__getSize__(functions));
			for(@SuppressWarnings("rawtypes") Function index : functions.get()) {
				runnablesExecutor.addRunnables(new Runnable() {
					@Override
					public void run() {
						index.execute();
					}
				});	
			}
			runnablesExecutor.execute();
		}
		return this;
	}
	
	@Override
	public Functions getFunctions() {
		return functions;
	}
	
	@Override
	public Functions getFunctions(Boolean injectIfNull) {
		if(functions == null && Boolean.TRUE.equals(injectIfNull))
			functions = __inject__(Functions.class);
		return functions;
	}
	
	@Override
	public FunctionsExecutor setFunctions(Functions functions) {
		this.functions = functions;
		return this;
	}
	
	@Override
	public FunctionsExecutor addFunctions(@SuppressWarnings("rawtypes") Collection<Function> functions) {
		getFunctions(Boolean.TRUE).add(functions);
		return this;
	}
	
	@Override
	public FunctionsExecutor addFunctions(@SuppressWarnings("rawtypes") Function... functions) {
		getFunctions(Boolean.TRUE).add(functions);
		return null;
	}
	
}
