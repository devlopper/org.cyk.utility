package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderTreeImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderTree,Serializable {
	private static final long serialVersionUID = 1L;
		
	@Override
	protected Class<? extends Row> __getRowClass__(Class<? extends Row> aClass) {
		SystemAction systemAction = getSystemAction();
		if(systemAction!=null)
			aClass = __inject__(DataHelper.class).getRowClass(systemAction);
		return aClass;
	}
	
}
