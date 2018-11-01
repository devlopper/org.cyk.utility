package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.menu.MenuItem;
import org.cyk.utility.client.controller.component.menu.MenuItems;
import org.cyk.utility.collection.CollectionHelper;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

public class MenuModelBuilder extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public MenuModel build(MenuItems menuItems) {
		MenuModel model = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(menuItems)) {
			model = new DefaultMenuModel();	
			build(model, menuItems.get());
		}
		return model;
	}
	
	public void build(Object parent,Collection<MenuItem> menuItems) {
		if(__inject__(CollectionHelper.class).isNotEmpty(menuItems)) {
			for(MenuItem index : menuItems) {
				Collection<MenuItem> children = __inject__(CollectionHelper.class).cast(MenuItem.class, index.getChildren());
		        if(__inject__(CollectionHelper.class).isEmpty(children)) {
		        	DefaultMenuItem item = new DefaultMenuItem(index.getName());
		        	addMenuElement(parent, item);	
		        }else {
		        	DefaultSubMenu subMenu = new DefaultSubMenu(index.getName());
		        	addMenuElement(parent, subMenu);
		        	build(subMenu, children);
		        }
			}
		}
	}
	
	private void addMenuElement(Object parent,MenuElement element) {
		if(parent instanceof MenuModel)
	        ((MenuModel)parent).addElement(element);
	     else if(parent instanceof DefaultSubMenu)
	    	 ((DefaultSubMenu)parent).addElement(element);	
	}
}
