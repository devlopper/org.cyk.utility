package org.cyk.utility.value;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface IsNullChecker extends FunctionWithPropertiesAsInput<Boolean> {

	Object getValue();
	IsNullChecker setValue(Object value);
	
}
