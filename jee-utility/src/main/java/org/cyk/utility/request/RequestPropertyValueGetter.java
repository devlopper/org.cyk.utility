package org.cyk.utility.request;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface RequestPropertyValueGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getRequest();
	RequestPropertyValueGetter setRequest(Object request);
	
	RequestProperty getProperty();
	RequestPropertyValueGetter setProperty(RequestProperty property);
	
}
