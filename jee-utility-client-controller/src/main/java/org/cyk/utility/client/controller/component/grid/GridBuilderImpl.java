package org.cyk.utility.client.controller.component.grid;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilderByClassMap;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.cell.Cells;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilders;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilders;
import org.cyk.utility.client.controller.component.grid.row.Rows;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.object.ObjectByClassMap;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public class GridBuilderImpl extends AbstractVisibleComponentBuilderImpl<Grid> implements GridBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ColumnBuilders columns;
	private RowBuilders rows;
	private ViewBuilder view;
	private Objects objects;
	private ColumnBuilder orderNumberColumn;
	private ColumnBuilder commandablesColumn;
	private CommandableBuilderByClassMap commandableMap;
	private CommandableBuilderByClassMap commandablesColumnCommandableMap;
	private ViewBuilderMap viewMap;
	private CommandableBuilder createRowCommandable,processRowCommandable;
	private Class<? extends Data> rowDataClass;
	private ObjectByClassMap commandablesColumnCommandablesNavigationsParametersMap;
	private SystemAction creationWindowSystemAction,processingWindowSystemAction;
	private Boolean isLazyLoadable;
	
	@Override
	protected void __execute__(Grid grid) {
		super.__execute__(grid);
		Boolean isLazyLoadable = getIsLazyLoadable();
		grid.setIsLazyLoadable(isLazyLoadable);
		
		Class<? extends Data> rowDataClass = getRowDataClass();
		
		Objects objects = getObjects();
		if(CollectionHelper.isNotEmpty(objects)) {
			for(Object index : objects.get()) {
				Object row = null;
				if(!(index instanceof org.cyk.utility.client.controller.data.Data))
					throw new RuntimeException("class "+index.getClass().getName()+" must implement Data interface");
				
				row = __inject__(org.cyk.utility.client.controller.data.RowBuilder.class).setGrid(this).setDataClass(rowDataClass).setData((Data) index).execute().getOutput();			
				if(row!=null)
					grid.getObjects(Boolean.TRUE).add(row);
			}
		}
		ViewBuilder view = getView(Boolean.TRUE);
		ComponentsBuilder componentsBuilder = view.getComponentsBuilder(Boolean.TRUE);
		LayoutBuilder layout = componentsBuilder.getLayout(Boolean.TRUE);
		LayoutTypeGrid layoutType = (LayoutTypeGrid) layout.getType();
		if(layoutType == null)
			layout.setType(layoutType = __inject__(LayoutTypeGrid.class));
		
		ColumnBuilders columns = getColumns();
		if(CollectionHelper.isNotEmpty(columns)) {
			ColumnBuilder orderNumberColumn = getOrderNumberColumn();
			if(orderNumberColumn == null && Boolean.TRUE.equals(layoutType.getIsHasOrderNumberColumn())) {
				orderNumberColumn = getOrderNumberColumn(Boolean.TRUE);
			}
			if(orderNumberColumn!=null)
				columns.addAt(orderNumberColumn,0);
			
			ColumnBuilder commandablesColumn = getCommandablesColumn();
			if(Boolean.TRUE.equals(layoutType.getIsHasCommandablesColumn())) {
				if(commandablesColumn == null)
					commandablesColumn = getCommandablesColumn(Boolean.TRUE);	
				
				ViewBuilder commandablesColumnView = commandablesColumn.getBodyView();
				if(commandablesColumnView == null)
					commandablesColumnView = commandablesColumn.getBodyView(Boolean.TRUE);
				
				if(commandablesColumnView!=null) {
					CommandableBuilders commandables = commandablesColumnView.getCommandables();
					if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(commandables))) {
						commandablesColumnView.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
						for(CommandableBuilder index : commandables.get()) {
							//__inject__(GridBuilderCommandableBuilderProcessor.class).setGridBuilder(this).setCommandableBuilder(index).execute();
							commandablesColumnView.getComponentsBuilder(Boolean.TRUE).addComponents(index);
						}
					}
				}
			}
			if(commandablesColumn!=null && commandablesColumn.getBodyView()!=null && commandablesColumn.getBodyView().getComponentsBuilder()!=null 
					&& CollectionHelper.isNotEmpty(commandablesColumn.getBodyView().getComponentsBuilder().getComponents()))
				columns.add(commandablesColumn);
			
			grid.setColumns(__inject__(Columns.class));
			Integer orderNumber = 0;
			for(ColumnBuilder index : columns.get()) {
				index.setOrderNumber(orderNumber++);
				grid.getColumns().add(index.execute().getOutput());
			}
		}
		RowBuilders rows = getRows();
		if(CollectionHelper.isNotEmpty(rows)) {
			grid.setRows(__inject__(Rows.class));
			Integer orderNumber = 0;
			for(RowBuilder index : rows.get()) {
				index.setOrderNumber(orderNumber++);
				Row row = index.execute().getOutput();
				grid.getRows().add(row);
				Cells cells = row.getCells();
				if(CollectionHelper.isNotEmpty(cells)) {
					for(Cell indexCell : cells.get())
						indexCell.setColumn(CollectionHelper.getElementAt(grid.getColumns(), (Integer)indexCell.getOrderNumber()));	
				}
				
			}
		}
		
		Rows dataTableRows = grid.getRows();
		Columns dataTableColumns = grid.getColumns();
		
		if(layoutType instanceof LayoutTypeGrid) {
			LayoutTypeGrid layoutTypeGrid = (LayoutTypeGrid) layoutType;
			
			layoutTypeGrid
					//.setIsHasHeader(Boolean.TRUE)
					//.setIsHasFooter(Boolean.TRUE)
					//.setIsHasOrderNumberColumn(Boolean.TRUE)
					//.setIsHasCommandablesColumn(Boolean.TRUE)
					.setRowCount(CollectionHelper.getSize(dataTableRows))
					.setColumnCount(CollectionHelper.getSize(dataTableColumns))
					;
			
			layout.addRoles(ComponentRole.GRID);
		}
		
		//Column Header
		//for(Column indexColumn : dataTableColumns.get())
		//	view.getComponentsBuilder(Boolean.TRUE).addComponents(dataTable.getCell(indexColumn, indexRow));
		
		if(CollectionHelper.isNotEmpty(dataTableRows)) {
			for(Row indexRow : dataTableRows.get()) {
				for(Column indexColumn : dataTableColumns.get())
					componentsBuilder.addComponents(grid.getCell(indexColumn, indexRow));
			}
		}
			
		grid.setView(view.execute().getOutput());
		ViewBuilderMap viewMap = getViewMap();
		if(viewMap == null)
			viewMap = __inject__(ViewBuilderMap.class);
		if(viewMap!=null) {
			ViewBuilder headerView = viewMap.get(ViewMap.HEADER);
			if(headerView == null) {
				CommandableBuilder createRowCommandable = getCreateRowCommandable();
				
				if(createRowCommandable!=null)
					__inject__(GridBuilderCommandableBuilderProcessor.class).setGridBuilder(this).setCommandableBuilder(createRowCommandable).execute();
				
				/*
				if(createRowCommandable == null || (createRowCommandable.getCommand()==null && createRowCommandable.getNavigation()==null)) {
					Class<?> rowDataClass = getRowDataClass();
					if(rowDataClass!=null) {
						createRowCommandable = __instanciateCreateRowCommandableBuilder__(rowDataClass);
					}
				}
				*/
				if(createRowCommandable!=null) {
					headerView = __inject__(ViewBuilder.class).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
					headerView.getComponentsBuilder().addComponents(createRowCommandable);
					viewMap.set(ViewMap.HEADER,headerView);
				}
			}
			/*
			ViewBuilder footerView = viewMap.get(ViewMap.FOOTER);
			if(footerView == null) {
				OutputStringTextBuilder footerText = getFooterText();
				if(footerText!=null) {
					footerView = __inject__(ViewBuilder.class).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
					footerView.getComponentsBuilder().addComponents(footerText);
					viewMap.set(ViewMap.FOOTER,footerView);
				}
			}
			*/
			
			Collection<Map.Entry<String,ViewBuilder>> entries = viewMap.getEntries();
			if(CollectionHelper.isNotEmpty(entries)) {
				grid.setViewMap(__inject__(ViewMap.class));
				for(Map.Entry<String,ViewBuilder> index : entries) {
					index.getValue().setPropertyIfNull(Properties.CONTEXT, Properties.getFromPath(getProperties(), Properties.CONTEXT));
					index.getValue().setPropertyIfNull(Properties.UNIFORM_RESOURCE_LOCATOR_MAP, Properties.getFromPath(getProperties(), Properties.UNIFORM_RESOURCE_LOCATOR_MAP));
					grid.getViewMap().set(index.getKey(),index.getValue().execute().getOutput());
				}
			}
		}
	}
	
	@Override
	public ColumnBuilders getColumns() {
		return columns;
	}

	@Override
	public ColumnBuilders getColumns(Boolean injectIfNull) {
		if(columns == null && Boolean.TRUE.equals(injectIfNull))
			columns = __inject__(ColumnBuilders.class);
		return columns;
	}

	@Override
	public GridBuilder setColumns(ColumnBuilders columns) {
		this.columns = columns;
		return this;
	}
	
	@Override
	public GridBuilder addColumns(Collection<ColumnBuilder> columns) {
		getColumns(Boolean.TRUE).add(columns);
		return this;
	}
	
	@Override
	public GridBuilder addColumns(ColumnBuilder...columns) {
		getColumns(Boolean.TRUE).add(columns);
		return this;
	}
	
	@Override
	public GridBuilder addColumnByFieldNameStrings(Collection<String> fieldNameStrings) {
		getColumns(Boolean.TRUE).add(__inject__(ColumnBuilder.class).addFieldNameStrings(fieldNameStrings));
		return this;
	}
	
	@Override
	public GridBuilder addColumnByFieldNameStrings(String... fieldNameStrings) {
		getColumns(Boolean.TRUE).add(__inject__(ColumnBuilder.class).addFieldNameStrings(fieldNameStrings));
		return this;
	}
	
	@Override
	public GridBuilder addColumnsByFieldNames(Collection<String> fieldNames) {
		if(CollectionHelper.isNotEmpty(fieldNames)) {
			for(String index : fieldNames)
				addColumnByFieldNameStrings(index);
		}
		return this;
	}
	
	@Override
	public GridBuilder addColumnsByFieldNames(String... fieldNames) {
		return addColumnsByFieldNames(CollectionHelper.listOf(fieldNames));
	}
	
	@Override
	public ColumnBuilder getColumnByFieldNameStrings(Collection<String> fieldNameStrings) {
		ColumnBuilders columns = getColumns();
		return columns == null ? null : columns.getByFieldNameStrings(fieldNameStrings);
	}
	
	@Override
	public ColumnBuilder getColumnByFieldNameStrings(String... fieldNameStrings) {
		return getColumnByFieldNameStrings(CollectionHelper.listOf(fieldNameStrings));
	}
	
	@Override
	public RowBuilders getRows() {
		return rows;
	}

	@Override
	public RowBuilders getRows(Boolean injectIfNull) {
		if(rows == null && Boolean.TRUE.equals(injectIfNull))
			rows = __inject__(RowBuilders.class);
		return rows;
	}

	@Override
	public GridBuilder setRows(RowBuilders rows) {
		this.rows = rows;
		return this;
	}
	
	@Override
	public GridBuilder addRows(Collection<RowBuilder> rows) {
		getRows(Boolean.TRUE).add(rows);
		return this;
	}
	
	@Override
	public GridBuilder addRows(RowBuilder...rows) {
		getRows(Boolean.TRUE).add(rows);
		return this;
	}
	
	@Override
	public ViewBuilder getView() {
		return view;
	}
	
	@Override
	public ViewBuilder getView(Boolean injectIfNull) {
		if(view == null && Boolean.TRUE.equals(injectIfNull))
			view = __inject__(ViewBuilder.class);
		return view;
	}
	
	@Override
	public GridBuilder setView(ViewBuilder view) {
		this.view = view;
		return this;
	}
	
	@Override
	public ViewBuilderMap getViewMap() {
		return viewMap;
	}

	@Override
	public ViewBuilderMap getViewMap(Boolean injectIfNull) {
		if(viewMap == null && Boolean.TRUE.equals(injectIfNull))
			viewMap = __inject__(ViewBuilderMap.class);
		return viewMap;
	}

	@Override
	public GridBuilder setViewMap(ViewBuilderMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}

	@Override
	public GridBuilder setViews(Object... keyValues) {
		getViewMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	@Override
	public ViewBuilder getView(String key) {
		ViewBuilderMap viewMap = getViewMap();
		return viewMap == null ? null : viewMap.get(key);
	}
	
	@Override
	public ViewBuilder getViewHeader(Boolean injectIfNull) {
		ViewBuilder view = null;
		ViewBuilderMap map = getViewMap(injectIfNull);
		if(map!=null) {
			view = map.get(ViewMap.HEADER);
			if(view == null && Boolean.TRUE.equals(injectIfNull))
				map.set(ViewMap.HEADER,view = __inject__(ViewBuilder.class));
		}
		return view;
	}
	
	@Override
	public GridBuilder addComponentBuildersToViewHeader(ComponentBuilder<?>... components) {
		for(ComponentBuilder<?> index : components)
			getViewHeader(Boolean.TRUE).addComponentBuilder(index);
		return this;
	}
	
	@Override
	public Objects getObjects() {
		return objects;
	}
	
	@Override
	public Objects getObjects(Boolean injectIfNull) {
		if(objects == null && Boolean.TRUE.equals(injectIfNull))
			objects = __inject__(Objects.class);
		return objects;
	}
	
	@Override
	public GridBuilder setObjects(Objects objects) {
		this.objects = objects;
		return this;
	}
	
	@Override
	public GridBuilder addObjects(Collection<Object> objects) {
		getObjects(Boolean.TRUE).add(objects);
		return this;
	}
	
	@Override
	public GridBuilder addObjects(Object... objects) {
		getObjects(Boolean.TRUE).add(objects);
		return this;
	}
	
	@Override
	public ColumnBuilder getOrderNumberColumn() {
		return orderNumberColumn;
	}

	@Override
	public ColumnBuilder getOrderNumberColumn(Boolean injectIfNull) {
		if(orderNumberColumn == null && Boolean.TRUE.equals(injectIfNull))
			orderNumberColumn = __inject__(ColumnBuilder.class);
		orderNumberColumn.setHeaderTextValue("#").addFieldNameStrings("orderNumber").setWidth("15");
		return orderNumberColumn;
	}

	@Override
	public GridBuilder setOrderNumberColumn(ColumnBuilder orderNumberColumn) {
		this.orderNumberColumn = orderNumberColumn;
		return this;
	}

	@Override
	public ColumnBuilder getCommandablesColumn() {
		return commandablesColumn;
	}

	@Override
	public ColumnBuilder getCommandablesColumn(Boolean injectIfNull) {
		if(commandablesColumn == null && Boolean.TRUE.equals(injectIfNull))
			commandablesColumn = __inject__(ColumnBuilder.class);
		commandablesColumn.setHeaderTextValue("Actions").setWidth("120");
		return commandablesColumn;
	}

	@Override
	public GridBuilder setCommandablesColumn(ColumnBuilder commandablesColumn) {
		this.commandablesColumn = commandablesColumn;
		return this;
	}
	
	@Override
	public ViewBuilder getCommandablesColumnBodyView(Boolean injectIfNull) {
		ColumnBuilder column = getCommandablesColumn(injectIfNull);
		return column == null ? null : column.getBodyView(injectIfNull);
	}
	
	@Override
	public GridBuilder addCommandablesToColumnBodyView(Collection<CommandableBuilder> commandables) {
		if(CollectionHelper.isNotEmpty(commandables))
			for(CommandableBuilder index : commandables) {
				index.setPropertyIfNull(Properties.CONTEXT, getContext());
				index.setPropertyIfNull(Properties.UNIFORM_RESOURCE_LOCATOR_MAP, getUniformResourceLocatorMap());
				getCommandablesColumnBodyView(Boolean.TRUE).addComponentBuilder(index);
			}
		return this;
	}
	
	@Override
	public GridBuilder addCommandablesToColumnBodyView(CommandableBuilder... commandables) {
		return addCommandablesToColumnBodyView(CollectionHelper.listOf(commandables));
	}
	
	@Override
	public CommandableBuilderByClassMap getCommandableMap() {
		return commandableMap;
	}
	
	@Override
	public CommandableBuilderByClassMap getCommandableMap(Boolean injectIfNull) {
		if(commandableMap == null && Boolean.TRUE.equals(injectIfNull))
			commandableMap = __inject__(CommandableBuilderByClassMap.class);
		return commandableMap;
	}
	
	@Override
	public GridBuilder setCommandableMap(CommandableBuilderByClassMap commandableMap) {
		this.commandableMap = commandableMap;
		return this;
	}
	
	@Override
	public CommandableBuilderByClassMap getCommandablesColumnCommandableMap() {
		return commandablesColumnCommandableMap;
	}
	
	@Override
	public CommandableBuilderByClassMap getCommandablesColumnCommandableMap(Boolean injectIfNull) {
		if(commandablesColumnCommandableMap == null && Boolean.TRUE.equals(injectIfNull))
			commandablesColumnCommandableMap = __inject__(CommandableBuilderByClassMap.class);
		return commandablesColumnCommandableMap;
	}
	
	@Override
	public GridBuilder setCommandablesColumnCommandableMap(CommandableBuilderByClassMap commandablesColumnCommandableMap) {
		this.commandablesColumnCommandableMap = commandablesColumnCommandableMap;
		return this;
	}
	
	@Override
	public ObjectByClassMap getCommandablesColumnCommandablesNavigationsParametersMap() {
		return commandablesColumnCommandablesNavigationsParametersMap;
	}
	
	@Override
	public GridBuilder setCommandablesColumnCommandablesNavigationsParametersMap(ObjectByClassMap commandablesColumnCommandablesNavigationsParametersMap) {
		this.commandablesColumnCommandablesNavigationsParametersMap = commandablesColumnCommandablesNavigationsParametersMap;
		return this;
	}
	
	@Override
	public ObjectByClassMap getCommandablesColumnCommandablesNavigationsParametersMap(Boolean injectIfNull) {
		if(commandablesColumnCommandablesNavigationsParametersMap == null && Boolean.TRUE.equals(injectIfNull))
			commandablesColumnCommandablesNavigationsParametersMap = __inject__(ObjectByClassMap.class);
		return commandablesColumnCommandablesNavigationsParametersMap;
	}
	
	@Override
	public GridBuilder setCommandablesColumnCommandablesNavigationsParameters(Object... parameters) {
		getCommandablesColumnCommandablesNavigationsParametersMap(Boolean.TRUE).set(parameters);
		return this;
	}
	
	@Override
	public GridBuilder setRowClass(Class<? extends org.cyk.utility.client.controller.data.Row> rowClass) {
		getRows(Boolean.TRUE).setRowClass(rowClass);
		return this;
	}
	
	@Override
	public Class<? extends org.cyk.utility.client.controller.data.Row> getRowClass() {
		return getRows(Boolean.TRUE).getRowClass();
	}
	
	@Override
	public GridBuilder setRowDataClass(Class<? extends Data> rowDataClass) {
		this.rowDataClass = rowDataClass;
		return this;
	}
	
	@Override
	public Class<? extends Data> getRowDataClass() {
		return rowDataClass;
	}
	
	@Override
	public CommandableBuilder getCreateRowCommandable() {
		return createRowCommandable;
	}
	
	@Override
	public CommandableBuilder getCreateRowCommandable(Boolean injectIfNull) {
		if(createRowCommandable == null && Boolean.TRUE.equals(injectIfNull))
			createRowCommandable = __inject__(CommandableBuilder.class);
		return createRowCommandable;
	}
	
	@Override
	public GridBuilder setCreateRowCommandable(CommandableBuilder createRowCommandable) {
		this.createRowCommandable = createRowCommandable;
		return this;
	}
	
	@Override
	public SystemAction getCreationWindowSystemAction() {
		return creationWindowSystemAction;
	}
	
	@Override
	public GridBuilder setCreationWindowSystemAction(SystemAction creationWindowSystemAction) {
		this.creationWindowSystemAction = creationWindowSystemAction;
		return this;
	}
	
	@Override
	public CommandableBuilder getProcessRowCommandable() {
		return processRowCommandable;
	}
	
	@Override
	public CommandableBuilder getProcessRowCommandable(Boolean injectIfNull) {
		if(processRowCommandable == null && Boolean.TRUE.equals(injectIfNull))
			processRowCommandable = __inject__(CommandableBuilder.class);
		return processRowCommandable;
	}
	
	@Override
	public GridBuilder setProcessRowCommandable(CommandableBuilder processRowCommandable) {
		this.processRowCommandable = processRowCommandable;
		return this;
	}
	
	@Override
	public SystemAction getProcessingWindowSystemAction() {
		return processingWindowSystemAction;
	}
	
	@Override
	public GridBuilder setProcessingWindowSystemAction(SystemAction processingWindowSystemAction) {
		this.processingWindowSystemAction = processingWindowSystemAction;
		return this;
	}
	
	@Override
	public Boolean getIsLazyLoadable() {
		return isLazyLoadable;
	}
	
	@Override
	public GridBuilder setIsLazyLoadable(Boolean isLazyLoadable) {
		this.isLazyLoadable = isLazyLoadable;
		return this;
	}
}
