package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MenuBuilderMapInstantiator {

	default MenuBuilderMap instantiate(Object key,Object request,Object window) {
		throw ThrowableHelper.NOT_YET_IMPLEMENTED;
	}
	
	default MenuBuilderMap instantiate(Object key,Object request) {
		return instantiate(key, request, null);
	}
	
	/**/
	
	static MenuBuilderMapInstantiator getInstance() {
		return Helper.getInstance(MenuBuilderMapInstantiator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
