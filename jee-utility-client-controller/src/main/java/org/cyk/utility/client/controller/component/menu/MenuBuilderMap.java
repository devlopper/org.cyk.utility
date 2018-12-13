package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.map.MapInstance;

@SuppressWarnings("rawtypes")
public interface MenuBuilderMap extends MapInstance<Class, MenuBuilder> {

	@Override MenuBuilderMap set(Object... keyValues);
	
}
