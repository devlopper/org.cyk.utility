package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import org.cyk.utility.client.controller.component.grid.Grid;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;

public interface ColumnBuilder extends UIComponentBuilder<Column,org.cyk.utility.client.controller.component.grid.column.Column> {

	DataTable getDataTable();
	ColumnBuilder setDataTable(DataTable dataTable);
	
	Grid getGrid();
	ColumnBuilder setGrid(Grid grid);
}
