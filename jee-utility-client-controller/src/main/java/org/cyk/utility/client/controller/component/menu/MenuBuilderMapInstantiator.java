package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
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
		MenuBuilderMapInstantiator instance = (MenuBuilderMapInstantiator) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(MenuBuilderMapInstantiator.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", MenuBuilderMapInstantiator.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
