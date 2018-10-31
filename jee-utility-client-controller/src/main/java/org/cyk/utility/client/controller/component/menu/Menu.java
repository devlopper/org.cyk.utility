package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Menu extends VisibleComponent {

	MenuItems getItems();
	Menu setItems(MenuItems items);
	
	MenuRenderType getRenderType();
	Menu setRenderType(MenuRenderType renderType);
	
}
