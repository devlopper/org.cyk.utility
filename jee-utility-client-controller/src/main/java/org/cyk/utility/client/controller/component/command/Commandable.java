package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Commandable extends VisibleComponent {

	CommandableRenderType getRenderType();
	Commandable setRenderType(CommandableRenderType renderType);
	
	String getName();
	Commandable setName(String name);
	
	Icon getIcon();
	Commandable setIcon(Icon icon);
	
	SystemAction getSystemAction();
	Commandable setSystemAction(SystemAction systemAction);
	
	String getUniformResourceIdentifier();
	Commandable setUniformResourceIdentifier(String uniformResourceIdentifier);
	
}
