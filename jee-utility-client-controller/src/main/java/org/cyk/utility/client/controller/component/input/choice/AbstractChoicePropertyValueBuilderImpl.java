package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractChoicePropertyValueBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements ChoicePropertyValueBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String propertyName;
	private Object object;
	
	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public ChoicePropertyValueBuilder setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public ChoicePropertyValueBuilder setObject(Object object) {
		this.object = object;
		return this;
	}

}
