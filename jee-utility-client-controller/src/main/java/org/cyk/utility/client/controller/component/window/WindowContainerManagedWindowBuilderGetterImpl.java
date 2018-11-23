package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<WindowContainerManagedWindowBuilder> implements WindowContainerManagedWindowBuilderGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	//private Boolean isGetSystemActionFromRequestParameters;
	
	@Override
	protected WindowContainerManagedWindowBuilder __execute__() throws Exception {
		SystemAction systemAction = getSystemAction();
		if(systemAction == null) {
			systemAction = __inject__(RequestParameterValueMapper.class).setParameterName(SystemAction.class).execute().getOutputAs(SystemAction.class);
		}
		Class<WindowContainerManagedWindowBuilder> windowContainerManagedWindowBuilderClass = __inject__(WindowContainerManagedWindowBuilderClassGetter.class).setSystemAction(systemAction)
				.execute().getOutput();
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = (WindowContainerManagedWindowBuilder) __inject__(windowContainerManagedWindowBuilderClass);
		windowContainerManagedWindowBuilder.setSystemAction(systemAction);
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
