package org.cyk.utility.client.controller.component;

import org.cyk.utility.method.MethodCallListener;

@Deprecated
public interface ComponentBuilderExecuteListener extends MethodCallListener {

	Component getComponent();
	ComponentBuilderExecuteListener setComponent(Component component);
	
	@Override ComponentBuilderExecuteListener setObject(Object object);
	
}
