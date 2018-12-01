package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.system.action.SystemAction;

public class RowListenerAdapter extends AbstractObject implements RowListener,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listenSystemAction(SystemAction systemAction) {}

	@Override
	public void listenNavigationBuilder(NavigationBuilder navigationBuilder) {}

	@Override
	public void listenNavigation(Navigation navigation) {}

}
