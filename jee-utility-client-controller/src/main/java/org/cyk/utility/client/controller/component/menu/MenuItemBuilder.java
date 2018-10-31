package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface MenuItemBuilder extends VisibleComponentBuilder<MenuItem> {
	
	String getName();
	MenuItemBuilder setName(String name);
	
	@Override MenuItemBuilder addChild(Object... child);
	
	@Override MenuItemBuilder setParent(Object parent);
	@Override MenuItemBuilder getParent();
	
}
