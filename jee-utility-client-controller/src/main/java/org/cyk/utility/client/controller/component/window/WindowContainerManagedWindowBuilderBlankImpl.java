package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderBlankImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderBlank,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __execute__() throws Exception {
		return super.__execute__().setTitleValue("BLANK PAGE");
	}
	
	@Override
	protected void __execute__(WindowBuilder window, SystemAction systemAction, Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		
	}

}
