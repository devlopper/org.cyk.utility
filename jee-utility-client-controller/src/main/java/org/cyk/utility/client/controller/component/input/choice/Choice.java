package org.cyk.utility.client.controller.component.input.choice;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Choice extends Objectable {

	Object getValue();
	Choice setValue(Object value);
	
	String getLabel();
	Choice setLabel(String label);
	
}
