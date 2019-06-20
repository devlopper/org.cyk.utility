package org.cyk.utility.function;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.runnable.RunnablesExecutor;

@Dependent
public class FunctionsExecutorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements FunctionsExecutor,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Functions functions;
	
	@Override
	protected void ____execute____() throws Exception {
		Functions functions = getFunctions();
		if(__injectCollectionHelper__().isNotEmpty(functions)) {
			RunnablesExecutor runnablesExecutor = __inject__(RunnablesExecutor.class);
			runnablesExecutor.getExecutorServiceBuilder(Boolean.TRUE).setCorePoolSize(__injectCollectionHelper__().getSize(functions));
			runnablesExecutor.getExecutorServiceBuilder(Boolean.TRUE).setMaximumPoolSize(__injectCollectionHelper__().getSize(functions));
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
	}
	
	@Override
	public Functions getFunctions() {
		return functions;
	}
	
	@Override
	public Functions getFunctions(Boolean injectIfNull) {
		return (Functions) __getInjectIfNull__(FIELD_FUNCTIONS, injectIfNull);
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
	
	private static final String FIELD_FUNCTIONS = "functions";
}
