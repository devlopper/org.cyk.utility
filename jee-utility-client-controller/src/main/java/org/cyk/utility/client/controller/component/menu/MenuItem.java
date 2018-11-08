package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;

public interface MenuItem extends VisibleComponent {

	Commandable getCommandable();
	MenuItem setCommandable(Commandable commandable);
	
	@Override MenuItem getParent();
	
}
