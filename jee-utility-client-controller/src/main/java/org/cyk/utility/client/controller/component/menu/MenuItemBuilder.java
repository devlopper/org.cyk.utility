package org.cyk.utility.client.controller.component.menu;

import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.event.EventName;

public interface MenuItemBuilder extends VisibleComponentBuilder<MenuItem> {
	
	CommandableBuilder getCommandable();
	CommandableBuilder getCommandable(Boolean injectIfNull);
	MenuItemBuilder setCommandable(CommandableBuilder commandable);
	
	MenuItemBuilder setCommandableName(String name);
	MenuItemBuilder setCommandableNameInternalizationKeyValue(Object key,InternationalizationKeyStringType type);
	MenuItemBuilder setCommandableNameInternalizationKeyValue(Object key);
	MenuItemBuilder setCommandableNavigationValue(String value);
	MenuItemBuilder setCommandableNavigationIdentifier(Object identifier);
	MenuItemBuilder setCommandableNavigationIdentifierAndParameters(Object identifier,Object[] parameters);
	MenuItemBuilder setCommandableNavigationIdentifierBuilderSystemAction(SystemAction systemAction,Object...parameters);
	MenuItemBuilder setCommandableOutputProperty(Object key,Object value);
	MenuItemBuilder setCommandableIcon(Icon icon);
	MenuItemBuilder addCommandableEvent(EventName name,String...scriptInstructions);
	
	MenuItemBuilder addCommandableRoles(ComponentRole...roles);
	
	MenuItemBuilder addEntitiesList(Collection<Class<?>> classes);
	
	default MenuItemBuilder addEntitiesList(Class<?>...classes) {
		if(ArrayHelper.isEmpty(classes))
			return this;
		addEntitiesList(CollectionHelper.listOf(classes));
		return this;
	}
	
	default MenuItemBuilder addEntityList(Class<?> klass) {
		if(klass == null)
			return this;
		return addEntitiesList(klass);
	}
	
	default MenuItemBuilder list(Collection<Class<?>> classes) {
		if(CollectionHelper.isEmpty(classes))
			return this;
		return addEntitiesList(classes);
	}
	
	default MenuItemBuilder list(Class<?>...classes) {
		if(ArrayHelper.isEmpty(classes))
			return this;
		return addEntitiesList(classes);
	}
	
	MenuItemBuilder addEntitiesTree(Class<?>...classes);
	MenuItemBuilder tree(Class<?>...classes);
	
	MenuItemBuilder addEntitiesListOrTree(Class<?>...classes);
	MenuItemBuilder listOrTree(Class<?>...classes);
	
	MenuItemBuilder addEntitiesSelect(Class<?>...classes);
	MenuItemBuilder addEntitySelect(Class<?> aClass,Class<? extends SystemAction> processingActionClass,String processingActionIdentifier);
	MenuItemBuilder addEntitySelect(Class<?> aClass,String processingActionIdentifier);
	MenuItemBuilder addEntitySelect(Class<?> aClass);
	
	MenuItemBuilder addEntityCreate(Class<?> aClass);
	MenuItemBuilder addEntitiesCreate(Class<?>...classes);
	
	@Override MenuItemBuilder addChild(Object... child);
	
	@Override MenuItemBuilder getLastChild();
	
	@Override MenuItemBuilder setParent(Object parent);
	@Override MenuItemBuilder getParent();
	
}
