package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.scope.ScopeSession;

public abstract class AbstractPageContainerManagedImpl extends org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud.AbstractPageContainerManagedImpl {
	private static final long serialVersionUID = -5590021276559897250L;

	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
		MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
		menuBuilder.addItems(
				__inject__(MenuItemBuilder.class).setName("Layout")
					.addChild(__inject__(MenuItemBuilder.class).setName("Index")
					,__inject__(MenuItemBuilder.class).setName("Table"))
				,__inject__(MenuItemBuilder.class).setName("Grid")
					.addChild(__inject__(MenuItemBuilder.class).setName("Index").setNavigationIdentifier("gridIndexView")
							)
					);
		//
		return __inject__(MenuBuilderMap.class).set(ScopeSession.class,menuBuilder);
	}
	
}
