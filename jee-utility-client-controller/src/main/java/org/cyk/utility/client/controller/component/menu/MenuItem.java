package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface MenuItem extends VisibleComponent {

	String getName();
	MenuItem setName(String name);
	
	@Override MenuItem getParent();
	
}
