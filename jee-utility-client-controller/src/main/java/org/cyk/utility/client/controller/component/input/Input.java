package org.cyk.utility.client.controller.component.input;

import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;

public interface Input<T> extends InputOutput<T> {

	Object getValueObject();
	void setValueObject(Object valueObject);
	
	T getInitialValue();
	Input<T> setInitialValue(T initialValue);
	
	OutputStringLabel getLabel();
	Input<T> setLabel(OutputStringLabel label);
	
	OutputStringMessage getMessage();
	Input<T> setMessage(OutputStringMessage message);
}
