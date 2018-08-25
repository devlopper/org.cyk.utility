package org.cyk.utility.client.controller.component;

public interface Command extends Component {

	CommandFunction getFunction();
	Command setFunction(CommandFunction function);
	CommandFunction getFunction(Boolean injectIfNull);
	
	Runnable getFunctionRunnable();
	Command setFunctionRunnable(Runnable runnable);
}
