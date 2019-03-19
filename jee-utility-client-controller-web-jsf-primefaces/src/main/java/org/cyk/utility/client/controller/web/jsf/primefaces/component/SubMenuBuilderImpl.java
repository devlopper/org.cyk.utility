package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.menu.MenuItem;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.collection.CollectionHelper;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

public class SubMenuBuilderImpl extends AbstractComponentBuilderImpl<org.primefaces.model.menu.Submenu, MenuItem> implements SubMenuBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected org.primefaces.model.menu.Submenu __execute__(MenuItem model, ValueExpressionMap valueExpressionMap) throws Exception {
		DefaultSubMenu subMenu = new DefaultSubMenu(model.getCommandable().getName());
    	
    	Object parent = getParent();
    	if(parent instanceof MenuModel)
	        ((MenuModel)parent).addElement(subMenu);
    	else if(parent instanceof DefaultSubMenu)
    		((DefaultSubMenu)parent).addElement(subMenu);	
    	
    	Collection<MenuItem> children = model.getChildrenInstanceOf(MenuItem.class);
    	if(__inject__(CollectionHelper.class).isNotEmpty(children)) {
			for(MenuItem index : children)
				MenuBuilderImpl.__buildItem__(subMenu, index);
    	}
    	
		return subMenu;
	}
	
	@Override
	public SubMenuBuilder setParent(Object parent) {
		return (SubMenuBuilder) super.setParent(parent);
	}
	
	@Override
	public SubMenuBuilder setModel(MenuItem model) {
		return (SubMenuBuilder) super.setModel(model);
	}

}
