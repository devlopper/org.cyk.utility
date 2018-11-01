package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;

public interface MenuItemBuilder extends VisibleComponentBuilder<MenuItem> {
	
	String getName();
	MenuItemBuilder setName(String name);
	
	NavigationBuilder getNavigation();
	NavigationBuilder getNavigation(Boolean injectIfNull);
	MenuItemBuilder setNavigation(NavigationBuilder navigation);
	MenuItemBuilder setNavigationIdentifier(Object identifier);
	
	@Override MenuItemBuilder addChild(Object... child);
	
	@Override MenuItemBuilder setParent(Object parent);
	@Override MenuItemBuilder getParent();
	
}
