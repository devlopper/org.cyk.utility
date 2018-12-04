package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class ChoiceImpl extends AbstractObject implements Choice,Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	private String label;
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Choice setValue(Object value) {
		this.value = value;
		return this;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public Choice setLabel(String label) {
		this.label = label;
		return this;
	}

	@Override
	public String toString() {
		return label;
	}
}
