package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;

public interface MenuBuilderGetter {

	default MenuBuilder get(Object key,Object request) {
		MenuBuilderMap menuBuilderMap = (MenuBuilderMap) SessionHelper.getAttributeValueFromRequest(SessionAttributeEnumeration.MENU_BUILDER_MAP,request);
		if(menuBuilderMap == null)
			SessionHelper.setAttributeValueFromRequest(SessionAttributeEnumeration.MENU_BUILDER_MAP
					,menuBuilderMap = DependencyInjection.inject(MenuBuilderMapGetter.class).setRequest(request).execute().getOutput(),request);
		return menuBuilderMap.get((Class<?>)key);
	}
	
	/**/
	
	static MenuBuilderGetter getInstance() {
		MenuBuilderGetter instance = (MenuBuilderGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(MenuBuilderGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", MenuBuilderGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
