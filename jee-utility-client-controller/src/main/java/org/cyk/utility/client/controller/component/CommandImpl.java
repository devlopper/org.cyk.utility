package org.cyk.utility.client.controller.component;

import java.io.Serializable;

public class CommandImpl extends AbstractComponentImpl implements Command,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFunction(__inject__(CommandFunction.class));
	}

	@Override
	public CommandFunction getFunction() {
		return (CommandFunction) getProperties().getFunction();
	}

	@Override
	public Command setFunction(CommandFunction function) {
		getProperties().setFunction(function);
		return this;
	}
	
	@Override
	public CommandFunction getFunction(Boolean injectIfNull) {
		CommandFunction function = getFunction();
		if(function == null && Boolean.TRUE.equals(injectIfNull))
			setFunction(function = __inject__(CommandFunction.class));
		return function;
	}
	
}
