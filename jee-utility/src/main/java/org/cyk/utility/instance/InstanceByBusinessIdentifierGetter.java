package org.cyk.utility.instance;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface InstanceByBusinessIdentifierGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getIdentifierValue();
	InstanceByBusinessIdentifierGetter setIdentifierValue(Object value);
	
}
