package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

public class WindowContainerManagedWindowBuilderIndexImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderIndex,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeSystemActionIsNull__(WindowBuilder window) {
		window.setTitleValue("Index");
		
	}

}
