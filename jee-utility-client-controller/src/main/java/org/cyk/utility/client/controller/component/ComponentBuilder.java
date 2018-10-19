package org.cyk.utility.client.controller.component;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ComponentBuilder<COMPONENT extends Component> extends FunctionWithPropertiesAsInput<COMPONENT> {
	
	Class<COMPONENT> getComponentClass();
	ComponentBuilder<COMPONENT> setComponentClass(Class<COMPONENT> componentClass);
	
	ComponentBuilder<COMPONENT> setOutputProperty(Object key,Object value);
}
