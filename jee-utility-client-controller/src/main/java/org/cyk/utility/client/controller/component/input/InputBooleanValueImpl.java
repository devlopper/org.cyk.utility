package org.cyk.utility.client.controller.component.input;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class InputBooleanValueImpl extends AbstractObject implements InputBooleanValue,Serializable {
	private static final long serialVersionUID = 1L;

	private String label;
	private Boolean value;
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public InputBooleanValue setLabel(String label) {
		this.label = label;
		return this;
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public InputBooleanValue setValue(Boolean value) {
		this.value = value;
		return this;
	}

}
