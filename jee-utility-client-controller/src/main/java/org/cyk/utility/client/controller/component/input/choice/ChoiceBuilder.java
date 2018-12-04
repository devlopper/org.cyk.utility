package org.cyk.utility.client.controller.component.input.choice;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ChoiceBuilder extends FunctionWithPropertiesAsInput<Object> {

	Object getValue();
	ChoiceBuilder setValue(Object value);
	
	String getLabel();
	ChoiceBuilder setLabel(String label);

}
