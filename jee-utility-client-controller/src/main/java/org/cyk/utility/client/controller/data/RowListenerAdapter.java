package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.system.action.SystemAction;

public class RowListenerAdapter extends AbstractObject implements RowListener,Serializable {
	private static final long serialVersionUID = 1L;

	private WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder;
	
	@Override
	public void listenSystemAction(SystemAction systemAction) {}

	@Override
	public void listenNavigationBuilder(NavigationBuilder navigationBuilder) {}

	@Override
	public void listenNavigation(Navigation navigation) {}

	@Override
	public WindowContainerManagedWindowBuilder getWindowContainerManagedWindowBuilder() {
		return windowContainerManagedWindowBuilder;
	}
	
	@Override
	public RowListener setWindowContainerManagedWindowBuilder(WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder) {
		this.windowContainerManagedWindowBuilder = windowContainerManagedWindowBuilder;
		return this;
	}
	
}
