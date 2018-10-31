package org.cyk.utility.client.controller.component.view;

import org.cyk.utility.map.MapInstance;

public interface ViewMap extends MapInstance<String, View> {

	@Override ViewMap set(Object... keyValues);
	
}
