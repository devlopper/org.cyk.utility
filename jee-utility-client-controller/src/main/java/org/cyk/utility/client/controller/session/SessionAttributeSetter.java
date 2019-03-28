package org.cyk.utility.client.controller.session;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface SessionAttributeSetter extends FunctionWithPropertiesAsInputAndVoidAsOutput {
	
	Object getAttribute();
	SessionAttributeSetter setAttribute(Object attribute);
	
	Object getValue();
	SessionAttributeSetter setValue(Object value);
}
