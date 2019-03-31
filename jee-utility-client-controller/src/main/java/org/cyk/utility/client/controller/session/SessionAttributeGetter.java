package org.cyk.utility.client.controller.session;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface SessionAttributeGetter extends FunctionWithPropertiesAsInput<Object> {
	
	Object getRequest();
	SessionAttributeGetter setRequest(Object request);
	
	Object getAttribute();
	SessionAttributeGetter setAttribute(Object attribute);
	
}
