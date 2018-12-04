package org.cyk.utility.client.controller.component.input.choice;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ChoicePropertyValueBuilder extends FunctionWithPropertiesAsInput<String> {

	String getPropertyName();
	ChoicePropertyValueBuilder setPropertyName(String propertyName);
	
	Object getObject();
	ChoicePropertyValueBuilder setObject(Object object);
	
}
