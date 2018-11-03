package org.cyk.utility.client.controller.component.grid;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilders;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilders;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public interface GridBuilder extends VisibleComponentBuilder<Grid> {

	ColumnBuilders getColumns();
	ColumnBuilders getColumns(Boolean injectIfNull);
	GridBuilder setColumns(ColumnBuilders columns);
	GridBuilder addColumns(Collection<ColumnBuilder> columns);
	GridBuilder addColumns(ColumnBuilder...columns);
	
	RowBuilders getRows();
	RowBuilders getRows(Boolean injectIfNull);
	GridBuilder setRows(RowBuilders rows);
	GridBuilder addRows(Collection<RowBuilder> rows);
	GridBuilder addRows(RowBuilder...rows);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	GridBuilder setView(ViewBuilder view);
}
