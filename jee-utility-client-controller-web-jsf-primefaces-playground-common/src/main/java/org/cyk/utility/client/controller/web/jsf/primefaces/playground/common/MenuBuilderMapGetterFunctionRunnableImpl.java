package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.scope.ScopeSession;

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
							
						,__inject__(MenuItemBuilder.class).setCommandableName("MyEntity")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Lister").setCommandableNavigationIdentifier("myEntityListView")
									,__inject__(MenuItemBuilder.class).setCommandableName("Créer").setCommandableNavigationIdentifierAndParameters("myEntityEditView",new Object[] {"class","myentity","action","create"})
									)
							);
				//
				setOutput(__inject__(MenuBuilderMap.class).set(ScopeSession.class,menuBuilder));
			}
		});
	}
	
}