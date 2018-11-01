package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.navigation.Navigation;

public interface MenuItem extends VisibleComponent {

	String getName();
	MenuItem setName(String name);
	
	Navigation getNavigation();
	MenuItem setNavigation(Navigation navigation);
	
	@Override MenuItem getParent();
	
}
