package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderIndexImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderIndex,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(WindowBuilder window, SystemAction systemAction, Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		window.setTitleValue("Index");
		
	}

}
