package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.system.action.SystemAction;

public interface MenuItemBuilder extends VisibleComponentBuilder<MenuItem> {
	
	CommandableBuilder getCommandable();
	CommandableBuilder getCommandable(Boolean injectIfNull);
	MenuItemBuilder setCommandable(CommandableBuilder commandable);
	
	MenuItemBuilder setCommandableName(String name);
	MenuItemBuilder setCommandableNameInternalizationKeyValue(Object key);
	MenuItemBuilder setCommandableNavigationIdentifier(Object identifier);
	MenuItemBuilder setCommandableNavigationIdentifierAndParameters(Object identifier,Object[] parameters);
	MenuItemBuilder setCommandableNavigationIdentifierBuilderSystemAction(SystemAction systemAction,Object...parameters);
	
	@Override MenuItemBuilder addChild(Object... child);
	
	@Override MenuItemBuilder setParent(Object parent);
	@Override MenuItemBuilder getParent();
	
}
