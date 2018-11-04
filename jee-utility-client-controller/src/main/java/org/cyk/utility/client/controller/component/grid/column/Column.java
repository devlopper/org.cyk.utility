package org.cyk.utility.client.controller.component.grid.column;

import org.cyk.utility.client.controller.component.grid.Dimension;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;

public interface Column extends Dimension {

	String getFieldName();
	Column setFieldName(String fieldName);
	
	String getValuePropertyName();
	Column setValuePropertyName(String valuePropertyName);
	
	ViewMap getViewMap();
	Column setViewMap(ViewMap viewMap);
	View getView(String key);
	
}
