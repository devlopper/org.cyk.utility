package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderEditImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderEdit,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Class<? extends Form> __getFormClass__(Class<? extends Form> aClass) {
		SystemAction systemAction = getSystemAction();
		if(systemAction!=null)
			aClass = __inject__(DataHelper.class).getFormClass(systemAction);
		return aClass;
	}
	
}
