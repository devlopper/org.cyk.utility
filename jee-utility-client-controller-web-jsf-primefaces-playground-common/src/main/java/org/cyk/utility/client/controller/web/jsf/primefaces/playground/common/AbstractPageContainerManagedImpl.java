package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.scope.ScopeSession;

public abstract class AbstractPageContainerManagedImpl extends org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl {
	private static final long serialVersionUID = -5590021276559897250L;

	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
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
					.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Lister").setCommandableNavigationIdentifierAndParameters("myEntityListWindow",new Object[] {"p","v","a","b"})
							,__inject__(MenuItemBuilder.class).setCommandableName("Créer").setCommandableNavigationIdentifier("myEntityEditWindow")
							)
					);
		//
		return __inject__(MenuBuilderMap.class).set(ScopeSession.class,menuBuilder);
	}
	
}
