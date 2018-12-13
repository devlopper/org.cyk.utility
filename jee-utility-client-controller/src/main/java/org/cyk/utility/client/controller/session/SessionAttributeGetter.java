package org.cyk.utility.client.controller.session;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface SessionAttributeGetter extends FunctionWithPropertiesAsInput<Object> {
	
	SessionAttributeEnumeration getAttribute();
	SessionAttributeGetter setAttribute(SessionAttributeEnumeration attribute);
	
}
