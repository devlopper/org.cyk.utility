package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionProcess;
import org.cyk.utility.__kernel__.system.action.SystemActionSelect;
import org.cyk.utility.__kernel__.system.action.SystemActionTree;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.icon.Icon;

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
			/*if(menuItem.getCommandable()!=null && menuItem.getCommandable().getNavigation()!=null && menuItem.getCommandable().getNavigation().getSystemAction()!=null) {
				__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClasses(menuItem.getCommandable().getNavigation().getSystemAction().getEntityClass());
			}
			*/
		}
		Collection<Object> children = getChildren();
		if(CollectionHelper.isNotEmpty(children)) {
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
		if(commandable == null && Boolean.TRUE.equals(injectIfNull))
			commandable = __inject__(CommandableBuilder.class);
		return commandable;
	}
	
	@Override
	public MenuItemBuilder setCommandableNavigationIdentifier(Object identifier) {
		if(identifier != null)
			getCommandable(Boolean.TRUE).getUniformResourceIdentifier(Boolean.TRUE).getPath(Boolean.TRUE).setIdentifier(identifier.toString());
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableNavigationIdentifierAndParameters(Object identifier,Object[] parameters) {
		//getCommandable(Boolean.TRUE).setNavigationIdentifierAndParameters(identifier,parameters);
		//return this;
		throw new RuntimeException("to be improved");
	}
	
	@Override
	public MenuItemBuilder setCommandableNavigationIdentifierBuilderSystemAction(SystemAction systemAction,Object... parameters) {
		getCommandable(Boolean.TRUE).getUniformResourceIdentifier(Boolean.TRUE).setSystemAction(systemAction);
		if(__inject__(ArrayHelper.class).isNotEmpty(parameters))
			throw new RuntimeException("to be improved");//getCommandable(Boolean.TRUE).setNavigationParameters(parameters);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableName(String name) {
		getCommandable(Boolean.TRUE).setName(name);
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableNameInternalizationKeyValue(Object key,InternationalizationKeyStringType type) {
		getCommandable(Boolean.TRUE).getNameInternationalization(Boolean.TRUE).setKey(InternationalizationHelper.buildKey(key,type));
		return this;
	}
	
	@Override
	public MenuItemBuilder setCommandableNameInternalizationKeyValue(Object key) {
		getCommandable(Boolean.TRUE).getNameInternationalization(Boolean.TRUE).setKey(InternationalizationHelper.buildKey(key));
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
	public MenuItemBuilder addEntitiesTree(Class<?>... classes) {
		for(Class<?> index : classes)
			addChild(__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(index)
					.setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionTree.class).setEntityClass(index)));
		return this;
	}
	
	@Override
	public MenuItemBuilder tree(Class<?>... classes) {
		return addEntitiesTree(classes);
	}
	
	@Override
	public MenuItemBuilder addEntitiesList(Class<?>... classes) {
		for(Class<?> index : classes)
			addEntityList(index);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntityList(Class<?> aClass) {
		return addChild(__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(aClass)
				.setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(aClass)));
	}
	
	@Override
	public MenuItemBuilder list(Class<?>... classes) {
		addEntitiesList(classes);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntitiesListOrTree(Class<?>... classes) {
		if(classes != null)
			for(Class<?> index : classes)
				if(ClassHelper.isInstanceOf(index, DataIdentifiedByString.class))
					tree(index);
				else
					list(index);
		return this;
	}
	
	@Override
	public MenuItemBuilder listOrTree(Class<?>... classes) {
		return addEntitiesListOrTree(classes);
	}
	
	@Override
	public MenuItemBuilder addEntitiesSelect(Class<?>... classes) {
		for(Class<?> index : classes)
			addEntitySelect(index,null);
		return this;
	}
	
	@Override
	public MenuItemBuilder addEntitySelect(Class<?> aClass,Class<? extends SystemAction> processingActionClass,String processingActionIdentifier) {
		MenuItemBuilder menuItemBuilder = __inject__(MenuItemBuilder.class);
		if(processingActionClass == null)
			processingActionClass = SystemActionProcess.class;
		SystemAction process = __inject__(processingActionClass);
		if(StringHelper.isBlank(processingActionIdentifier))
			processingActionIdentifier = (String) process.getIdentifier();
		else
			process.setIdentifier(processingActionIdentifier);
		
		menuItemBuilder.setCommandableNameInternalizationKeyValue(processingActionIdentifier,InternationalizationKeyStringType.VERB);
		SystemAction action = __inject__(SystemActionSelect.class).setEntityClass(aClass).setNextAction(process);
		return addChild(menuItemBuilder.setCommandableNavigationIdentifierBuilderSystemAction(action));
	}
	
	@Override
	public MenuItemBuilder addEntitySelect(Class<?> aClass,String processingActionIdentifier) {
		return addEntitySelect(aClass,SystemActionProcess.class,processingActionIdentifier);
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
	
}
