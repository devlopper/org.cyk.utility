package org.cyk.utility.client.controller.component.grid;

import java.util.Collection;

import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilderByClassMap;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilders;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilders;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilderMap;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.object.ObjectByClassMap;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface GridBuilder extends VisibleComponentBuilder<Grid> {

	Boolean getIsLazyLoadable();
	GridBuilder setIsLazyLoadable(Boolean isLazyLoadable);
	
	ColumnBuilders getColumns();
	ColumnBuilders getColumns(Boolean injectIfNull);
	GridBuilder setColumns(ColumnBuilders columns);
	GridBuilder addColumns(Collection<ColumnBuilder> columns);
	GridBuilder addColumns(ColumnBuilder...columns);
	GridBuilder addColumnByFieldNameStrings(Collection<String> fieldNameStrings);
	GridBuilder addColumnByFieldNameStrings(String...fieldNameStrings);
	GridBuilder addColumnsByFieldNames(Collection<String> fieldNames);
	GridBuilder addColumnsByFieldNames(String...fieldNames);
	
	ColumnBuilder getColumnByFieldNameStrings(Collection<String> fieldNameStrings);
	ColumnBuilder getColumnByFieldNameStrings(String...fieldNameStrings);
	
	RowBuilders getRows();
	RowBuilders getRows(Boolean injectIfNull);
	GridBuilder setRows(RowBuilders rows);
	GridBuilder addRows(Collection<RowBuilder> rows);
	GridBuilder addRows(RowBuilder...rows);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	GridBuilder setView(ViewBuilder view);
	
	ViewBuilderMap getViewMap();
	ViewBuilderMap getViewMap(Boolean injectIfNull);
	GridBuilder setViewMap(ViewBuilderMap viewMap);
	GridBuilder setViews(Object...keyValues);
	ViewBuilder getView(String key);
	ViewBuilder getViewHeader(Boolean injectIfNull);
	
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
	
	ObjectByClassMap getCommandablesColumnCommandablesNavigationsParametersMap();
	ObjectByClassMap getCommandablesColumnCommandablesNavigationsParametersMap(Boolean injectIfNull);
	GridBuilder setCommandablesColumnCommandablesNavigationsParametersMap(ObjectByClassMap commandablesColumnCommandablesNavigationsParametersMap);
	GridBuilder setCommandablesColumnCommandablesNavigationsParameters(Object...parameters);
	
	ViewBuilder getCommandablesColumnBodyView(Boolean injectIfNull);
	GridBuilder addCommandablesToColumnBodyView(Collection<CommandableBuilder> commandables);
	GridBuilder addCommandablesToColumnBodyView(CommandableBuilder...commandables);
	//GridBuilder addNavigationCommandablesBySystemActionClasses(Collection<Class<? extends SystemAction>> systemActionClasses);
	//GridBuilder addNavigationCommandablesBySystemActionClasses(Class<? extends SystemAction>...systemActionClasses);
	
	GridBuilder addComponentBuildersToViewHeader(ComponentBuilder<?>...components);
	
	CommandableBuilderByClassMap getCommandableMap();
	CommandableBuilderByClassMap getCommandableMap(Boolean injectIfNull);
	GridBuilder setCommandableMap(CommandableBuilderByClassMap commandableMap);
	
	CommandableBuilderByClassMap getCommandablesColumnCommandableMap();
	CommandableBuilderByClassMap getCommandablesColumnCommandableMap(Boolean injectIfNull);
	GridBuilder setCommandablesColumnCommandableMap(CommandableBuilderByClassMap commandablesColumnCommandableMap);
	
	GridBuilder setRowClass(Class<? extends Row> rowClass);
	Class<? extends Row> getRowClass();
	
	GridBuilder setRowDataClass(Class<? extends Data> rowDataClass);
	Class<? extends Data> getRowDataClass();
	
	GridBuilder setCreationWindowSystemAction(SystemAction creationWindowSystemAction);
	SystemAction getCreationWindowSystemAction();
	
	CommandableBuilder getCreateRowCommandable();
	CommandableBuilder getCreateRowCommandable(Boolean injectIfNull);
	GridBuilder setCreateRowCommandable(CommandableBuilder createRowCommandable);
	
	GridBuilder setProcessingWindowSystemAction(SystemAction processingWindowSystemAction);
	SystemAction getProcessingWindowSystemAction();
	
	CommandableBuilder getProcessRowCommandable();
	CommandableBuilder getProcessRowCommandable(Boolean injectIfNull);
	GridBuilder setProcessRowCommandable(CommandableBuilder processRowCommandable);
	
	/**/
}
