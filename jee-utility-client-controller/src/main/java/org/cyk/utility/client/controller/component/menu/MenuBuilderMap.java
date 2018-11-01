package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.map.MapInstance;
import org.cyk.utility.scope.Scope;

public interface MenuBuilderMap extends MapInstance<Scope, MenuBuilder> {

	@Override MenuBuilderMap set(Object... keyValues);
	
}
