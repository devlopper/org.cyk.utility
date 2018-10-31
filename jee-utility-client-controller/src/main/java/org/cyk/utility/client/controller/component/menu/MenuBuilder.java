package org.cyk.utility.client.controller.component.menu;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface MenuBuilder extends VisibleComponentBuilder<Menu> {

	MenuItemBuilders getItems();
	MenuItemBuilders getItems(Boolean injectIfNull);
	MenuBuilder setItems(MenuItemBuilders items);
	MenuBuilder addItems(Collection<MenuItemBuilder> items);	
	MenuBuilder addItems(MenuItemBuilder...items);
	
	MenuRenderType getRenderType();
	MenuBuilder setRenderType(MenuRenderType renderType);
	
}
