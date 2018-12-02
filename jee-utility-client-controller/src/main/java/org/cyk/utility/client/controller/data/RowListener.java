package org.cyk.utility.client.controller.data;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.system.action.SystemAction;

public interface RowListener extends Objectable {

	WindowContainerManagedWindowBuilder getWindowContainerManagedWindowBuilder();
	RowListener setWindowContainerManagedWindowBuilder(WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder);
	
	void listenSystemAction(SystemAction systemAction);
	void listenNavigationBuilder(NavigationBuilder navigationBuilder);
	void listenNavigation(Navigation navigation);
}
