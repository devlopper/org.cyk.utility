package org.cyk.utility.context;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface ContextParameterValueGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getContext();
	ContextParameterValueGetter setContext(Object context);
	
	String getName();
	ContextParameterValueGetter setName(String name);
	
	Object getNullValue();
	ContextParameterValueGetter setNullValue(Object nullValue);
}
