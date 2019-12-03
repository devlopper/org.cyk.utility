package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

public class WindowContainerManagedWindowBuilderBlankImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderBlank,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __execute__() throws Exception {
		return super.__execute__().setTitleValue("BLANK PAGE");
	}
	
}
