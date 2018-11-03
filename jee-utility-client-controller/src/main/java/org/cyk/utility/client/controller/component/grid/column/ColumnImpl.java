package org.cyk.utility.client.controller.component.grid.column;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.AbstractDimensionImpl;
import org.cyk.utility.client.controller.component.view.ViewMap;

public class ColumnImpl extends AbstractDimensionImpl implements Column,Serializable {
	private static final long serialVersionUID = 1L;

	private ViewMap viewMap;
	
	@Override
	public ViewMap getViewMap() {
		return viewMap;
	}

	@Override
	public Column setViewMap(ViewMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}

}
