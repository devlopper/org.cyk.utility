package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuItem;
import org.cyk.utility.client.controller.component.menu.MenuItems;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.primefaces.model.menu.DefaultMenuModel;

public class MenuBuilderImpl extends AbstractComponentBuilderImpl<org.primefaces.model.menu.MenuModel, Menu> implements MenuBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected org.primefaces.model.menu.MenuModel __execute__(Menu model, ValueExpressionMap valueExpressionMap) throws Exception {
		DefaultMenuModel menu = null;
		MenuItems menuItems = model.getItems();
		if(CollectionHelper.isNotEmpty(menuItems)) {
			menu = new DefaultMenuModel();	
			__buildItem__(menu, menuItems.get());
		}
		return menu;
	}
	
	@Override
	public MenuBuilder setModel(Menu model) {
		return (MenuBuilder) super.setModel(model);
	}

	public static void __buildItem__(Object parent,Collection<MenuItem> menuItems) {
		if(CollectionHelper.isNotEmpty(menuItems)) {
			for(MenuItem index : menuItems)
				__buildItem__(parent, index);
		}
	}
	
	public static void __buildItem__(Object parent,MenuItem menuItem) {
		if(Boolean.TRUE.equals(menuItem.isHasChildrenInstanceOf(MenuItem.class)))
			__inject__(SubMenuBuilder.class).setParent(parent).setModel(menuItem).execute();
		else
			__inject__(MenuItemBuilder.class).setParent(parent).setModel(menuItem).execute();
	}
}
