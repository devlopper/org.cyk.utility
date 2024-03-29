package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.Command;
import org.cyk.utility.client.controller.component.command.CommandBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public class CommandBuilderImpl extends AbstractInvisibleComponentBuilderImpl<Command> implements CommandBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandFunction function;
	private WindowContainerManaged windowContainerManaged;
	private String containerContextDependencyInjectionBeanName;
	private Boolean isSynchronous;
	//private Data data;
	
	@Override
	protected void __execute__(Command command) {
		super.__execute__(command);
		CommandFunction function = getFunction();
		command.setFunction(function);
		WindowContainerManaged windowContainerManaged = getWindowContainerManaged();
		command.setWindowContainerManaged(windowContainerManaged);		
		String containerContextDependencyInjectionBeanName = getContainerContextDependencyInjectionBeanName();
		if(StringHelper.isBlank(containerContextDependencyInjectionBeanName)) {
			if(windowContainerManaged!=null)
				containerContextDependencyInjectionBeanName = windowContainerManaged.getContextDependencyInjectionBeanName();
		}
		command.setContainerContextDependencyInjectionBeanName(containerContextDependencyInjectionBeanName);
		Boolean isSynchronous = ValueHelper.defaultToIfNull(getIsSynchronous(),Boolean.FALSE);
		command.setIsSynchronous(isSynchronous);
	}
	
	@Override
	public CommandFunction getFunction() {
		return function;
	}

	@Override
	public CommandBuilder setFunction(CommandFunction function) {
		this.function = function;
		return this;
	}

	@Override
	public CommandFunction getFunction(Boolean injectIfNull) {
		if(function == null && Boolean.TRUE.equals(injectIfNull))
			function = __inject__(CommandFunction.class);
		return function;
	}
	
	@Override
	public WindowContainerManaged getWindowContainerManaged() {
		return windowContainerManaged;
	}

	@Override
	public CommandBuilder setWindowContainerManaged(WindowContainerManaged windowContainerManaged) {
		this.windowContainerManaged = windowContainerManaged;
		return this;
	}
	
	@Override
	public String getContainerContextDependencyInjectionBeanName() {
		return containerContextDependencyInjectionBeanName;
	}
	
	@Override
	public CommandBuilder setContainerContextDependencyInjectionBeanName(String containerContextDependencyInjectionBeanName) {
		this.containerContextDependencyInjectionBeanName = containerContextDependencyInjectionBeanName;
		return this;
	}
	
	@Override
	public Boolean getIsSynchronous() {
		return isSynchronous;
	}
	
	@Override
	public CommandBuilder setIsSynchronous(Boolean isSynchronous) {
		this.isSynchronous = isSynchronous;
		return this;
	}
	
	
	/**/
	
	public static final String FIELD_FUNCTION = "function";

	

}
