package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.__kernel__.Helper;
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
		return Helper.getInstance(MenuBuilderGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
