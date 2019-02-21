package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.icon.Icon;
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
	MenuItemBuilder setCommandableOutputProperty(Object key,Object value);
	MenuItemBuilder setCommandableIcon(Icon icon);
	
	MenuItemBuilder addCommandableRoles(ComponentRole...roles);
	
	MenuItemBuilder addEntitiesList(Class<?>...classes);
	MenuItemBuilder addEntityList(Class<?> aClass);
	
	MenuItemBuilder addEntitiesSelect(Class<?>...classes);
	MenuItemBuilder addEntitySelect(Class<?> aClass,String processingActionIdentifier);
	MenuItemBuilder addEntitySelect(Class<?> aClass);
	
	MenuItemBuilder addEntityCreate(Class<?> aClass);
	MenuItemBuilder addEntitiesCreate(Class<?>...classes);
	
	@Override MenuItemBuilder addChild(Object... child);
	
	@Override MenuItemBuilder getLastChild();
	
	@Override MenuItemBuilder setParent(Object parent);
	@Override MenuItemBuilder getParent();
	
}
