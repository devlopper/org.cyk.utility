package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.scope.ScopeSession;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionSelect;

public class MenuBuilderMapGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<MenuBuilderMapGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public MenuBuilderMapGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
				menuBuilder.addItems(
						__inject__(MenuItemBuilder.class).setCommandableName("Layout")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index")
							,__inject__(MenuItemBuilder.class).setCommandableName("Table"))
						,__inject__(MenuItemBuilder.class).setCommandableName("Commandable")					
									.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Button Index").setCommandableNavigationIdentifier("commandableButtonIndexView")
											,__inject__(MenuItemBuilder.class).setCommandableName("Commandable Navigation Index").setCommandableNavigationIdentifier("commandableNavigationIndexView")
									)
									
						,__inject__(MenuItemBuilder.class).setCommandableName("Grid")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("gridIndexView")
									)
						,__inject__(MenuItemBuilder.class).setCommandableName("View")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("viewIndexView")
									)
						,__inject__(MenuItemBuilder.class).setCommandableName("Navigation")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("navigationIndexView")
									)
						
						,__inject__(MenuItemBuilder.class).setCommandableName("Entités")
							.addChild(
								__inject__(MenuItemBuilder.class).setCommandableName("List").addChild(
									__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(MyEntity.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(MyEntity.class))
									,__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(VerySimpleEntity.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(VerySimpleEntity.class))
									)
								,__inject__(MenuItemBuilder.class).setCommandableName("Selection pour traitement").addChild(
										__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(MyEntity.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionSelect.class).setEntityClass(MyEntity.class))
										,__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(VerySimpleEntity.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionSelect.class).setEntityClass(VerySimpleEntity.class))
										)
								)
						
								
							
						,__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(MyEntity.class)
							.addChild(__inject__(MenuItemBuilder.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(MyEntity.class))
									,__inject__(MenuItemBuilder.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionCreate.class).setEntityClass(MyEntity.class))
									)
							);
				//
				setOutput(__inject__(MenuBuilderMap.class).set(ScopeSession.class,menuBuilder));
			}
		});
	}
	
}