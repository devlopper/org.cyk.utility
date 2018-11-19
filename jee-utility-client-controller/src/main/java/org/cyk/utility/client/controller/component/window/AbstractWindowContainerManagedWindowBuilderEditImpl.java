package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderEditImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderEdit,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		Class<?> entityClass = getEntityClass();
		if(entityClass!=null) {
			setFormClass(__inject__(ControllerLayer.class).getFormClass(entityClass, getSystemAction()));
			SystemAction systemAction = getSystemAction();
			if(systemAction!=null)
				systemAction.getEntities(Boolean.TRUE).setElementClass(entityClass);
		}
	}
	
}
