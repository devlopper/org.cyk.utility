package org.cyk.utility.client.controller.component.datatable;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.column.Columns;

public class DataTableImpl extends AbstractVisibleComponentImpl implements DataTable,Serializable {
	private static final long serialVersionUID = 1L;

	private Columns columns;
	
	@Override
	public Columns getColumns() {
		return columns;
	}

	@Override
	public DataTable setColumns(Columns columns) {
		this.columns = columns;
		return this;
	}

}
