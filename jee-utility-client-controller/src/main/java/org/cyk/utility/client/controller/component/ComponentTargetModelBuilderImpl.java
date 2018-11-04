package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ComponentTargetModelBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ComponentTargetModelBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Component component;
	
	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public ComponentTargetModelBuilder setComponent(Component component) {
		this.component = component;
		return this;
	}

}
