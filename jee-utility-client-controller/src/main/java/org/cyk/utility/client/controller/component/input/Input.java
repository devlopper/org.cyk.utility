package org.cyk.utility.client.controller.component.input;

import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;

public interface Input<T> extends InputOutput<T> {

	Object getValueObject();
	void setValueObject(Object valueObject);
	
	T getInitialValue();
	Input<T> setInitialValue(T initialValue);
	
	OutputStringLabel getLabelComponent();
	Input<T> setLabelComponent(OutputStringLabel labelComponent);
	Input<T> setLabelComponentValue(Object value);
	
	OutputStringMessage getMessageComponent();
	Input<T> setMessageComponent(OutputStringMessage messageComponent);
}
