package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.client.controller.component.InvisibleComponentBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public interface CommandBuilder extends InvisibleComponentBuilder<Command> {

	CommandFunction getFunction();
	CommandBuilder setFunction(CommandFunction function);
	CommandFunction getFunction(Boolean injectIfNull);
	/*
	Data getData();
	CommandBuilder setData(Data data);
	*/
	
	WindowContainerManaged getWindowContainerManaged();
	CommandBuilder setWindowContainerManaged(WindowContainerManaged windowContainerManaged);
	
	String getContainerContextDependencyInjectionBeanName();
	CommandBuilder setContainerContextDependencyInjectionBeanName(String containerContextDependencyInjectionBeanName);
}
