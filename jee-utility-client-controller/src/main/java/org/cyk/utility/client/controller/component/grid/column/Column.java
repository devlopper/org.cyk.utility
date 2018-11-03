package org.cyk.utility.client.controller.component.grid.column;

import org.cyk.utility.client.controller.component.grid.Dimension;
import org.cyk.utility.client.controller.component.view.ViewMap;

public interface Column extends Dimension {

	ViewMap getViewMap();
	Column setViewMap(ViewMap viewMap);
	
}
