package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MenuBuilderMapGetter {

	default MenuBuilderMap get(Object key,Object request,Object window) {
		MenuBuilderMap menuBuilderMap = (MenuBuilderMap) SessionHelper.getAttributeValueFromRequest(key,request);
		if(menuBuilderMap == null)
			SessionHelper.setAttributeValueFromRequest(key,MenuBuilderMapInstantiator.getInstance().instantiate(key, request),request);
		return menuBuilderMap;
	}
	
	default MenuBuilderMap get(Object key,Object request) {
		return get(key, request, null);
	}
	
	/**/
	
	static MenuBuilderMapGetter getInstance() {
		return Helper.getInstance(MenuBuilderMapGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
