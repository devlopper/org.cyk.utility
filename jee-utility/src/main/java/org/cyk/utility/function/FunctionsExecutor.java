package org.cyk.utility.function;

import java.util.Collection;

@Deprecated
public interface FunctionsExecutor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Functions getFunctions();
	Functions getFunctions(Boolean injectIfNull);
	FunctionsExecutor setFunctions(Functions functions);
	FunctionsExecutor addFunctions(@SuppressWarnings("rawtypes") Collection<Function> functions);
	FunctionsExecutor addFunctions(@SuppressWarnings("rawtypes") Function...functions);
}
