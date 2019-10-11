package org.cyk.utility.request;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface RequestParameterValueGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getRequest();
	RequestParameterValueGetter setRequest(Object request);
	
	Object getParameterName();
	RequestParameterValueGetter setParameterName(Object parameterName);
	
}
