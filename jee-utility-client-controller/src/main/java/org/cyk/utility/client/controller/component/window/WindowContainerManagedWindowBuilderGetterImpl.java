package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public class WindowContainerManagedWindowBuilderGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<WindowContainerManagedWindowBuilder> implements WindowContainerManagedWindowBuilderGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private WindowContainerManaged containerManaged;
	private SystemAction systemAction;
	
	@Override
	protected WindowContainerManagedWindowBuilder __execute__() throws Exception {
		WindowContainerManaged containerManaged = getContainerManaged();
		SystemAction systemAction = getSystemAction();
		if(systemAction == null) {
			if(containerManaged!=null)
				systemAction = containerManaged.getSystemAction();
		}
		
		Class<WindowContainerManagedWindowBuilder> windowContainerManagedWindowBuilderClass = __inject__(WindowHelper.class).
				getWindowContainerManagedWindowBuilderClass(systemAction);
		
		if(windowContainerManagedWindowBuilderClass == null) {
			if(systemAction == null)
				windowContainerManagedWindowBuilderClass = (Class<WindowContainerManagedWindowBuilder>) ClassHelper.getByName(WindowContainerManagedWindowBuilderBlank.class.getName());
		}
		
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = null;
		if(windowContainerManagedWindowBuilderClass==null) {
			throw new RuntimeException(getClass().getSimpleName()+" : No WindowContainerManagedWindowBuilder found for "+(systemAction == null ? "UNKNOWN_ACTION" : systemAction.getIdentifier()));
		}else {
			windowContainerManagedWindowBuilder = (WindowContainerManagedWindowBuilder) __inject__(windowContainerManagedWindowBuilderClass);
			windowContainerManagedWindowBuilder.setSystemAction(systemAction);	
		}
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

	@Override
	public WindowContainerManaged getContainerManaged() {
		return containerManaged;
	}

	@Override
	public WindowContainerManagedWindowBuilderGetter setContainerManaged(WindowContainerManaged containerManaged) {
		this.containerManaged = containerManaged;
		return this;
	}

}
