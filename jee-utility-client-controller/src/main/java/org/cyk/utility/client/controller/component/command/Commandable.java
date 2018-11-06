package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.navigation.Navigation;

public interface Commandable extends VisibleComponent {

	CommandableRenderType getRenderType();
	Commandable setRenderType(CommandableRenderType renderType);
	
	String getName();
	Commandable setName(String name);
	
	Navigation getNavigation();
	Commandable setNavigation(Navigation navigation);

	/**/
	
}
