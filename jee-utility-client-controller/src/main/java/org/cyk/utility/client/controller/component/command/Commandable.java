package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Commandable extends VisibleComponent {

	CommandableRenderType getRenderType();
	Commandable setRenderType(CommandableRenderType renderType);
	
	String getName();
	Commandable setName(String name);

	/**/
	
}
