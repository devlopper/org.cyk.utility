package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderReadImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderRead,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Class<? extends Form> __getFormClass__(Class<? extends Form> aClass) {
		SystemAction systemAction = getSystemAction();
		if(systemAction!=null)
			aClass = __inject__(ControllerLayer.class).getFormClass(systemAction);
		return aClass;
	}
	
}
