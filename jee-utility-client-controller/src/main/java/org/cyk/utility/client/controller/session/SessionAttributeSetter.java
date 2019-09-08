package org.cyk.utility.client.controller.session;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

@Deprecated
public interface SessionAttributeSetter extends FunctionWithPropertiesAsInputAndVoidAsOutput {
	
	Object getRequest();
	SessionAttributeSetter setRequest(Object request);
	
	Object getAttribute();
	SessionAttributeSetter setAttribute(Object attribute);
	
	Object getValue();
	SessionAttributeSetter setValue(Object value);
}
