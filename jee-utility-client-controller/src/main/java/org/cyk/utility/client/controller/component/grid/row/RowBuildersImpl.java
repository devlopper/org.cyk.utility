package org.cyk.utility.client.controller.component.grid.row;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class RowBuildersImpl extends AbstractCollectionInstanceImpl<RowBuilder> implements RowBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<? extends Row> rowClass;
	
	@Override
	public Class<? extends Row> getRowClass() {
		return rowClass;
	}

	@Override
	public RowBuilders setRowClass(Class<? extends Row> rowClass) {
		this.rowClass = rowClass;
		return this;
	}

}
