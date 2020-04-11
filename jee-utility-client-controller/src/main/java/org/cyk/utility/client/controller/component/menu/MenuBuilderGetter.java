package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MenuBuilderGetter {

	default MenuBuilder get(Object mapKey,Object key,Object request) {
		MenuBuilderMap menuBuilderMap = MenuBuilderMapGetter.getInstance().get(mapKey, request);
		if(menuBuilderMap == null)
			return null;
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
	
	Value INSTANCE = new Value();
}
