package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.client.controller.component.InvisibleComponent;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public interface Command extends InvisibleComponent {

	CommandFunction getFunction();
	Command setFunction(CommandFunction function);
	CommandFunction getFunction(Boolean injectIfNull);

	WindowContainerManaged getWindowContainerManaged();
	Command setWindowContainerManaged(WindowContainerManaged windowContainerManaged);
	
	String getContainerContextDependencyInjectionBeanName();
	Command setContainerContextDependencyInjectionBeanName(String containerContextDependencyInjectionBeanName);
}
