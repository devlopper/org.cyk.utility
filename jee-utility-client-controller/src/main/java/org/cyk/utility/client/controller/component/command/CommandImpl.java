package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInvisibleComponentImpl;

public class CommandImpl extends AbstractInvisibleComponentImpl implements Command,Serializable {
	private static final long serialVersionUID = 1L;

	private String windowContainerVariableName;
	
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

	@Override
	public String getWindowContainerVariableName() {
		return windowContainerVariableName;
	}

	@Override
	public Command setWindowContainerVariableName(String windowContainerVariableName) {
		this.windowContainerVariableName = windowContainerVariableName;
		return this;
	}
	
}
