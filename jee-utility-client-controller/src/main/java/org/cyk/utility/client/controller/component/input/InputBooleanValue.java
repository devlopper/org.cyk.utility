package org.cyk.utility.client.controller.component.input;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface InputBooleanValue extends Objectable {
	
	String getLabel();
	InputBooleanValue setLabel(String label);
	
	Boolean getValue();
	InputBooleanValue setValue(Boolean value);
}
