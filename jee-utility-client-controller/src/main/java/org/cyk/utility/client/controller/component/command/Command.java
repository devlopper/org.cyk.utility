package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.client.controller.component.InvisibleComponent;

public interface Command extends InvisibleComponent {

	CommandFunction getFunction();
	Command setFunction(CommandFunction function);
	CommandFunction getFunction(Boolean injectIfNull);
	
}
