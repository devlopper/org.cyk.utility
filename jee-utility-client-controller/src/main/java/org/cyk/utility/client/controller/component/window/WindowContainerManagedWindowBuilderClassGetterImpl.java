package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.clazz.AbstractClassFunctionImpl;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionRead;

@SuppressWarnings("rawtypes")
public class WindowContainerManagedWindowBuilderClassGetterImpl extends AbstractClassFunctionImpl implements WindowContainerManagedWindowBuilderClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected Class __execute__() throws Exception {
		Class clazz = null;
		SystemAction systemAction = getSystemAction();
		clazz = __inject__(ControllerLayer.class).getWindowContainerManagedWindowBuilderClass(systemAction);
		if(clazz == null) {
			if(systemAction instanceof SystemActionRead || systemAction instanceof SystemActionAdd) {
				Class<?> entityClass = null;
				if(systemAction.getEntities()!=null)
					entityClass = systemAction.getEntities().getElementClass();
				systemAction = __inject__(SystemActionCreate.class).setEntityClass(entityClass);
				clazz = __inject__(ControllerLayer.class).getWindowContainerManagedWindowBuilderClass(systemAction);
			}
		}
		return clazz;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public WindowContainerManagedWindowBuilderClassGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
