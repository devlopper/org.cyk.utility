package org.cyk.utility.client.controller.component;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ComponentTargetModelBuilder extends FunctionWithPropertiesAsInput<Object> {

	Component getComponent();
	ComponentTargetModelBuilder setComponent(Component component);
	
}
