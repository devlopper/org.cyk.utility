package org.cyk.utility.client.controller.component.grid;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilderByClassMap;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilders;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilders;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.object.Objects;

public interface GridBuilder extends VisibleComponentBuilder<Grid> {

	ColumnBuilders getColumns();
	ColumnBuilders getColumns(Boolean injectIfNull);
	GridBuilder setColumns(ColumnBuilders columns);
	GridBuilder addColumns(Collection<ColumnBuilder> columns);
	GridBuilder addColumns(ColumnBuilder...columns);
	GridBuilder addColumnByFieldNameStrings(Collection<String> fieldNameStrings);
	GridBuilder addColumnByFieldNameStrings(String...fieldNameStrings);
	
	RowBuilders getRows();
	RowBuilders getRows(Boolean injectIfNull);
	GridBuilder setRows(RowBuilders rows);
	GridBuilder addRows(Collection<RowBuilder> rows);
	GridBuilder addRows(RowBuilder...rows);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	GridBuilder setView(ViewBuilder view);
	
	Objects getObjects();
	Objects getObjects(Boolean injectIfNull);
	GridBuilder setObjects(Objects objects);
	GridBuilder addObjects(Collection<Object> objects);
	GridBuilder addObjects(Object...objects);
	
	ColumnBuilder getOrderNumberColumn();
	ColumnBuilder getOrderNumberColumn(Boolean injectIfNull);
	GridBuilder setOrderNumberColumn(ColumnBuilder orderNumberColumn);
	
	ColumnBuilder getCommandablesColumn();
	ColumnBuilder getCommandablesColumn(Boolean injectIfNull);
	GridBuilder setCommandablesColumn(ColumnBuilder commandablesColumn);
	
	ViewBuilder getCommandablesColumnBodyView(Boolean injectIfNull);
	//GridBuilder addNavigationCommandablesBySystemActionClasses(Collection<Class<? extends SystemAction>> systemActionClasses);
	//GridBuilder addNavigationCommandablesBySystemActionClasses(Class<? extends SystemAction>...systemActionClasses);
	
	CommandableBuilderByClassMap getCommandableMap();
	CommandableBuilderByClassMap getCommandableMap(Boolean injectIfNull);
	GridBuilder setCommandableMap(CommandableBuilderByClassMap commandableMap);
	
	CommandableBuilderByClassMap getCommandablesColumnCommandableMap();
	CommandableBuilderByClassMap getCommandablesColumnCommandableMap(Boolean injectIfNull);
	GridBuilder setCommandablesColumnCommandableMap(CommandableBuilderByClassMap commandablesColumnCommandableMap);
	
	GridBuilder setRowClass(Class<? extends Row> rowClass);
	
	/**/
	
	
}
