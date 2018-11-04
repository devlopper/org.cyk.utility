package org.cyk.utility.client.controller.component.grid;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
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
import org.cyk.utility.client.controller.component.layout.LayoutType;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.object.Objects;

public class GridBuilderImpl extends AbstractVisibleComponentBuilderImpl<Grid> implements GridBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ColumnBuilders columns;
	private RowBuilders rows;
	private ViewBuilder view;
	private Objects objects;
	
	@Override
	protected void __execute__(Grid dataTable) {
		super.__execute__(dataTable);
		Objects objects = getObjects();
		dataTable.setObjects(objects);
		
		ColumnBuilders columns = getColumns();
		if(__injectCollectionHelper__().isNotEmpty(columns)) {
			dataTable.setColumns(__inject__(Columns.class));
			Integer orderNumber = 0;
			for(ColumnBuilder index : columns.get()) {
				index.setOrderNumber(orderNumber++);
				dataTable.getColumns().add(index.execute().getOutput());
			}
		}
		
		RowBuilders rows = getRows();
		if(__injectCollectionHelper__().isNotEmpty(rows)) {
			dataTable.setRows(__inject__(Rows.class));
			Integer orderNumber = 0;
			for(RowBuilder index : rows.get()) {
				index.setOrderNumber(orderNumber++);
				Row row = index.execute().getOutput();
				dataTable.getRows().add(row);
				Cells cells = row.getCells();
				if(__injectCollectionHelper__().isNotEmpty(cells)) {
					for(Cell indexCell : cells.get())
						indexCell.setColumn(__injectCollectionHelper__().getElementAt(dataTable.getColumns(), (Integer)indexCell.getOrderNumber()));	
				}
				
			}
		}
		
		ViewBuilder view = getView();
		if(view == null) {
			view = __inject__(ViewBuilder.class);
		}
		
		ComponentsBuilder componentsBuilder = view.getComponentsBuilder(Boolean.TRUE);
		LayoutBuilder layout = componentsBuilder.getLayout(Boolean.TRUE);
		LayoutType layoutType = layout.getType();
		if(layoutType == null)
			layout.setType(layoutType = __inject__(LayoutTypeGrid.class));
				
		Rows dataTableRows = dataTable.getRows();
		Columns dataTableColumns = dataTable.getColumns();
		
		Boolean isHasHeader = null;
		Boolean isHasFooter = null;
		Boolean isHasOrderNumberColumn = null;
		Boolean isHasCommandablesColumn = null;
		
		if(layoutType instanceof LayoutTypeGrid) {
			LayoutTypeGrid layoutTypeGrid = (LayoutTypeGrid) layoutType;
			
			layoutTypeGrid
					//.setIsHasHeader(Boolean.TRUE)
					//.setIsHasFooter(Boolean.TRUE)
					//.setIsHasOrderNumberColumn(Boolean.TRUE)
					//.setIsHasCommandablesColumn(Boolean.TRUE)
					.setRowCount(__injectCollectionHelper__().getSize(dataTableRows))
					.setColumnCount(__injectCollectionHelper__().getSize(dataTableColumns))
					;
			isHasHeader = layoutTypeGrid.getIsHasHeader();
			isHasFooter = layoutTypeGrid.getIsHasFooter();
			isHasOrderNumberColumn = layoutTypeGrid.getIsHasOrderNumberColumn();
			isHasCommandablesColumn = layoutTypeGrid.getIsHasCommandablesColumn();
			layout.addRoles(ComponentRole.GRID);
		}
		
		if(Boolean.TRUE.equals(isHasHeader))
			componentsBuilder.addComponents(__inject__(OutputStringTextBuilder.class).setValue("En tête de la grille"));
		
		//Column Header
		//for(Column indexColumn : dataTableColumns.get())
		//	view.getComponentsBuilder(Boolean.TRUE).addComponents(dataTable.getCell(indexColumn, indexRow));
		
		if(__injectCollectionHelper__().isNotEmpty(dataTableRows)) {
			for(Row indexRow : dataTableRows.get()) {
				if(Boolean.TRUE.equals(isHasOrderNumberColumn))
					componentsBuilder.addComponents(__inject__(OutputStringTextBuilder.class).setValue(indexRow.getOrderNumber().toString()));
				for(Column indexColumn : dataTableColumns.get())
					componentsBuilder.addComponents(dataTable.getCell(indexColumn, indexRow));
				if(Boolean.TRUE.equals(isHasCommandablesColumn))
					componentsBuilder.addComponents(__inject__(OutputStringTextBuilder.class).setValue("ACTIONS"));
			}
		}
		
		if(Boolean.TRUE.equals(isHasFooter))
			componentsBuilder.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Pied de la grille"));
			
		dataTable.setView(view.execute().getOutput());
	}
	
	@Override
	public ColumnBuilders getColumns() {
		return columns;
	}

	@Override
	public ColumnBuilders getColumns(Boolean injectIfNull) {
		return (ColumnBuilders) __getInjectIfNull__(FIELD_COLUMNS, injectIfNull);
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
	public RowBuilders getRows() {
		return rows;
	}

	@Override
	public RowBuilders getRows(Boolean injectIfNull) {
		return (RowBuilders) __getInjectIfNull__(FIELD_ROWS, injectIfNull);
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
		return (ViewBuilder) __getInjectIfNull__(FIELD_VIEW, injectIfNull);
	}
	
	@Override
	public GridBuilder setView(ViewBuilder view) {
		this.view = view;
		return this;
	}
	
	@Override
	public Objects getObjects() {
		return objects;
	}
	
	@Override
	public Objects getObjects(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_OBJECTS, injectIfNull);
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
	
	public static final String FIELD_COLUMNS = "columns";
	public static final String FIELD_ROWS = "rows";
	public static final String FIELD_VIEW = "view";
	public static final String FIELD_OBJECTS = "objects";
}
