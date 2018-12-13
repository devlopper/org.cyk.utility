package org.cyk.utility.client.controller.session;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface SessionAttributeSetter extends FunctionWithPropertiesAsInputAndVoidAsOutput {
	
	SessionAttributeEnumeration getAttribute();
	SessionAttributeSetter setAttribute(SessionAttributeEnumeration attribute);
	
	Object getValue();
	SessionAttributeSetter setValue(Object value);
}
