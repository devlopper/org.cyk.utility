package org.cyk.utility.client.controller.component.datatable;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.column.ColumnBuilders;

public interface DataTableBuilder extends VisibleComponentBuilder<DataTable> {

	ColumnBuilders getColumns();
	ColumnBuilders getColumns(Boolean injectIfNull);
	DataTableBuilder setColumns(ColumnBuilders columns);
	DataTableBuilder addColumns(Collection<ColumnBuilder> columns);
	DataTableBuilder addColumns(ColumnBuilder...columns);
	
}
