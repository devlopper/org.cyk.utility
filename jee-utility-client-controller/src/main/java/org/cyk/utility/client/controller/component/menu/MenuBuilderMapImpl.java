package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;

import org.cyk.utility.map.AbstractMapInstanceImpl;
import org.cyk.utility.scope.Scope;

public class MenuBuilderMapImpl extends AbstractMapInstanceImpl<Scope, MenuBuilder> implements MenuBuilderMap,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MenuBuilderMap set(Object... keyValues) {
		return (MenuBuilderMap) super.set(keyValues);
	}
	
}
