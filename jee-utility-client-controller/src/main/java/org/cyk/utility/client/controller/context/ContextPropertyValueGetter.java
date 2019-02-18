package org.cyk.utility.client.controller.context;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ContextPropertyValueGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getContext();
	ContextPropertyValueGetter setContext(Object context);
	
	ContextPropertyName getPropertyName();
	ContextPropertyValueGetter setPropertyName(ContextPropertyName propertyName);
	
}
