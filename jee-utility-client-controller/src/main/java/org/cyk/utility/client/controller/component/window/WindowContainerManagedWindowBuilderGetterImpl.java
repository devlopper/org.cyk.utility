package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<WindowContainerManagedWindowBuilder> implements WindowContainerManagedWindowBuilderGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected WindowContainerManagedWindowBuilder __execute__() throws Exception {
		String className = null;
		Class<?> aClass = null;
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = (WindowContainerManagedWindowBuilder) __inject__(aClass);
		return windowContainerManagedWindowBuilder;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public WindowContainerManagedWindowBuilderGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
