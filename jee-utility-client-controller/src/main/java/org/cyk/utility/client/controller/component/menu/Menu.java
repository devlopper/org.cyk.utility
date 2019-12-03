package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;

public interface Menu extends VisibleComponent {

	MenuItems getItems();
	Menu setItems(MenuItems items);
	
	MenuRenderType getRenderType();
	Menu setRenderType(MenuRenderType renderType);
	
	Commandable getCommandableByIdentifier(String identifier);
	
	/**/
	
	String CONTROL_PANEL = "MENU_CONTROL_PANEL";
}
