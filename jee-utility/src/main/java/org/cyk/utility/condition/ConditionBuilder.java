package org.cyk.utility.condition;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ConditionBuilder extends FunctionWithPropertiesAsInput<Condition> {

	Boolean getValue();
	ConditionBuilder setValue(Boolean value);
	
	ConditionBuilder setMessage(String message);
	String getMessage();
	
	ConditionBuilder setIdentifier(Object identifier);
	
}
