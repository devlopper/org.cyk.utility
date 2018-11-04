package org.cyk.utility.client.controller.component.grid.column;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.AbstractDimensionImpl;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;

public class ColumnImpl extends AbstractDimensionImpl implements Column,Serializable {
	private static final long serialVersionUID = 1L;

	private ViewMap viewMap;
	private String fieldName,valuePropertyName;
	
	@Override
	public ViewMap getViewMap() {
		return viewMap;
	}

	@Override
	public Column setViewMap(ViewMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}

	@Override
	public View getView(String key) {
		ViewMap viewMap = getViewMap();
		return viewMap == null ? null : viewMap.get(key);
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public Column setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}
	
	@Override
	public String getValuePropertyName() {
		return valuePropertyName;
	}
	
	@Override
	public Column setValuePropertyName(String valuePropertyName) {
		this.valuePropertyName = valuePropertyName;
		return this;
	}
}
