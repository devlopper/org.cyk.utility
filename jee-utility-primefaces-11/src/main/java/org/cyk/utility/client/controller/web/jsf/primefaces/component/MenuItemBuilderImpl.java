package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.component.menu.MenuItem;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

public class MenuItemBuilderImpl extends AbstractComponentBuilderImpl<org.primefaces.model.menu.MenuItem, MenuItem> implements MenuItemBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected org.primefaces.model.menu.MenuItem __execute__(MenuItem model, ValueExpressionMap valueExpressionMap) throws Exception {
		DefaultMenuItem item = new DefaultMenuItem(model.getCommandable().getName());
    	if(StringHelper.isNotBlank(model.getCommandable().getUniformResourceIdentifier()))
    		item.setUrl(model.getCommandable().getUniformResourceIdentifier());
    	if(model.getCommandable().getCommand()!=null) {
    		item.setCommand(__injectJavaServerFacesHelper__().getCommandFunctionCallExpressionLanguage(model.getCommandable()));
			item.setUpdate(__injectPrimefacesHelper__().computeAttributeUpdate(model.getCommandable()));
    	}
    	item.setIcon((String)model.getProperties().getIcon());
    	if(StringHelper.isBlank(item.getIcon()))
    		item.setIcon((String)model.getCommandable().getProperties().getIcon());
    	
    	item.setOnclick((String)model.getProperties().getOnClick());
    	if(StringHelper.isBlank(item.getOnclick()))
    		item.setOnclick((String)model.getCommandable().getProperties().getOnClick());
    	
    	Object parent = getParent();
    	if(parent instanceof MenuModel)
	        ((MenuModel)parent).addElement(item);
    	else if(parent instanceof DefaultSubMenu)
    		((DefaultSubMenu)parent).addElement(item);	
		return item;
	}
	
	@Override
	public MenuItemBuilder setParent(Object parent) {
		return (MenuItemBuilder) super.setParent(parent);
	}
	
	@Override
	public MenuItemBuilder setModel(MenuItem model) {
		return (MenuItemBuilder) super.setModel(model);
	}

}
