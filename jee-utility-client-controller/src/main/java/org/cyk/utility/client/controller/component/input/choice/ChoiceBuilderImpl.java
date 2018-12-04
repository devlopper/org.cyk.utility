package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ChoiceBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ChoiceBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	private String label;
	
	@Override
	protected Object __execute__() throws Exception {
		Choice choice = __inject__(Choice.class);
		Object value = getValue();
		choice.setValue(value);
		String label = getLabel();
		if(label == null)
			label = value == null ? "NULL LABEL" : value.toString();
		choice.setLabel(label);
		return choice;
	}
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public ChoiceBuilder setValue(Object value) {
		this.value = value;
		return this;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public ChoiceBuilder setLabel(String label) {
		this.label = label;
		return this;
	}

}
