package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.collection.CollectionHelper;
import org.primefaces.component.datatable.DataTable;

public class DataTableBuilder extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public DataTable build(Grid grid) {
		DataTable dataTable = new DataTable();
		Columns columns = grid.getColumns();
		if(__inject__(CollectionHelper.class).isNotEmpty(columns))
			for(Column index : columns.get())
				addColumn(dataTable, index);
		return dataTable;
	}
	
	private void addColumn(DataTable dataTable,Column column) {
		org.primefaces.component.column.Column __column__ = new org.primefaces.component.column.Column();
		__column__.setHeaderText("Title");
		dataTable.getColumns().add(__column__);
	}
	
}
