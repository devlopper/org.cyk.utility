package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentTargetModelBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Primefaces
public class ComponentTargetModelBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ComponentTargetModelBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Component component;
	
	@Override
	protected Object __execute__() throws Exception {
		Component component = getComponent();
		Object target = __inject__(ComponentBuilderHelper.class).build(component);
		return target;		
	}
	
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
