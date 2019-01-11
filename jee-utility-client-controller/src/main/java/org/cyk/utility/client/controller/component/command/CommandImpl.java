package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInvisibleComponentImpl;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public class CommandImpl extends AbstractInvisibleComponentImpl implements Command,Serializable {
	private static final long serialVersionUID = 1L;

	private WindowContainerManaged windowContainerManaged;
	private String containerContextDependencyInjectionBeanName;
	
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
	public WindowContainerManaged getWindowContainerManaged() {
		return windowContainerManaged;
	}

	@Override
	public Command setWindowContainerManaged(WindowContainerManaged windowContainerManaged) {
		this.windowContainerManaged = windowContainerManaged;
		return this;
	}
	
	@Override
	public String getContainerContextDependencyInjectionBeanName() {
		return containerContextDependencyInjectionBeanName;
	}
	
	@Override
	public Command setContainerContextDependencyInjectionBeanName(String containerContextDependencyInjectionBeanName) {
		this.containerContextDependencyInjectionBeanName = containerContextDependencyInjectionBeanName;
		return this;
	}
}
