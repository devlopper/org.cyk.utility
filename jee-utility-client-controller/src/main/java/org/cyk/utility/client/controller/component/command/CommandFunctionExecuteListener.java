package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.method.MethodCallListener;

public interface CommandFunctionExecuteListener extends MethodCallListener {

	//Component getComponent();
	//ComponentBuilderExecuteListener setComponent(Component component);
	
	@Override CommandFunctionExecuteListener setObject(Object object);
	
}
