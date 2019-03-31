package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionSelect;

public class MenuItemBuilderImpl extends AbstractVisibleComponentBuilderImpl<MenuItem> implements MenuItemBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandableBuilder commandable;
	
	@Override
	protected void __execute__(MenuItem menuItem) {
		super.__execute__(menuItem);
		CommandableBuilder commandable = getCommandable();
		if(commandable!=null) {
			if(commandable.getRequest() == null)
				commandable.setRequest(getRequest());
			menuItem.setCommandable(commandable.execute().getOutput());
		}
		Collection<Object> children = getChildren();
		if(__injectCollectionHelper__().isNotEmpty(children)) {
			for(Object index : children) {
				if(index instanceof MenuItemBuilder) {
					if(((MenuItemBuilder)index).getRequest() == null)
						((MenuItemBuilder)index).setRequest(getRequest());
					menuItem.addChild( ((MenuItemBuilder)index).execute().getOutput() );
				}
			}
		}
	}
	
	@Override
	public MenuItemBuilder setCommandableIcon(Icon icon) {
		getCommandable(Boolean.TRUE).setIcon(icon);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandable(CommandableBuilder commandable) {
		this.commandable = commandable;
		return this;
	}
	
	@Override
	public CommandableBuilder getCommandable() {
		return commandable;
	}
	
	@Override
	public CommandableBuilder getCommandable(Boolean injectIfNull) {
		return (CommandableBuilder) __getInjectIfNull__(FIELD_COMMANDABLE, injectIfNull);
	}
	
	@Override
	public MenuItemBuilder setCommandableNavigationIdentifier(Object identifier) {
		getCommandable(Boolean.TRUE).setNavigationIdentifier(identifier);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableNavigationIdentifierAndParameters(Object identifier,Object[] parameters) {
		getCommandable(Boolean.TRUE).setNavigationIdentifierAndParameters(identifier,parameters);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableNavigationIdentifierBuilderSystemAction(SystemAction systemAction,Object... parameters) {
		getCommandable(Boolean.TRUE).setNavigationIdentifierBuilderSystemAction(systemAction)/*.setNavigationParameters(parameters)*/;
		if(__inject__(ArrayHelper.class).isNotEmpty(parameters))
			getCommandable(Boolean.TRUE).setNavigationParameters(parameters);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableName(String name) {
		getCommandable(Boolean.TRUE).setName(name);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableNameInternalizationKeyValue(Object key) {
		getCommandable(Boolean.TRUE).getNameInternalization(Boolean.TRUE).setKeyValue(key);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableOutputProperty(Object key, Object value) {
		getCommandable(Boolean.TRUE).setOutputProperty(key, value);
		return this;
	}
	
	@Override
	public MenuItemBuilder addCommandableRoles(ComponentRole... roles) {
		getCommandable(Boolean.TRUE).addRoles(roles);
		return this;
	}
	
	@Override
	public MenuItemBuilder addCommandableEvent(EventName name, String... scriptInstructions) {
		getCommandable(Boolean.TRUE).addEvent(name, scriptInstructions);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntitiesList(Class<?>... classes) {
		for(Class<?> index : classes)
			addEntityList(index);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntitiesSelect(Class<?>... classes) {
		for(Class<?> index : classes)
			addEntitySelect(index,null);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntityList(Class<?> aClass) {
		return addChild(__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(aClass)
				.setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(aClass)));
	}
	
	@Override
	public MenuItemBuilder addEntitySelect(Class<?> aClass,String processingActionIdentifier) {
		MenuItemBuilder menuItemBuilder = __inject__(MenuItemBuilder.class);
		SystemAction process = __inject__(SystemActionProcess.class);
		if(__injectStringHelper__().isNotBlank(processingActionIdentifier)) {
			process.setIdentifier(processingActionIdentifier);
			menuItemBuilder.setCommandableNameInternalizationKeyValue(processingActionIdentifier);
		}
		SystemAction action = __inject__(SystemActionSelect.class).setEntityClass(aClass).setNextAction(process);
		return addChild(menuItemBuilder.setCommandableNavigationIdentifierBuilderSystemAction(action));
	}
	
	@Override
	public MenuItemBuilder addEntityCreate(Class<?> aClass) {
		return addChild(__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(aClass)
				.setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionCreate.class).setEntityClass(aClass)));
	}
	
	@Override
	public MenuItemBuilder addEntitiesCreate(Class<?>... classes) {
		for(Class<?> index : classes)
			addEntityCreate(index);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntitySelect(Class<?> aClass) {
		return addEntitySelect(aClass, null);
	}
	
	@Override
	public MenuItemBuilder addChild(Object... child) {
		return (MenuItemBuilder) super.addChild(child);
	}
	
	@Override
	public MenuItemBuilder getLastChild() {
		return (MenuItemBuilder) super.getLastChild();
	}
	
	@Override
	public MenuItemBuilder setParent(Object parent) {
		return (MenuItemBuilder) super.setParent(parent);
	}
	
	@Override
	public MenuItemBuilder getParent() {
		return (MenuItemBuilder) super.getParent();
	}
	
	/**/
	
	public static final String FIELD_COMMANDABLE = "commandable";
	
}
