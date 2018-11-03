package org.cyk.utility.client.controller.component.view;

import org.cyk.utility.map.MapInstance;

public interface ViewBuilderMap extends MapInstance<String, ViewBuilder> {

	@Override ViewBuilderMap set(Object... keyValues);
	
}
