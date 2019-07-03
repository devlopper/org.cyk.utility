package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.model.ListDataModel;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.cell.Cells;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.grid.row.Rows;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.client.controller.web.jsf.primefaces.LazyDataModel;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.value.ValueHelper;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;

public class DataTableBuilderImpl extends AbstractComponentBuilderImpl<DataTable, Grid> implements DataTableBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected DataTable __execute__(Grid grid, ValueExpressionMap valueExpressionMap) throws Exception {
		DataTable dataTable = new DataTable();
		dataTable.setVar("indexRow");
		dataTable.setReflow(Boolean.TRUE);
		dataTable.setWidgetVar((String) grid.getProperties().getWidgetVar());
		if(grid.getStyle()!=null)
			dataTable.setStyleClass(grid.getStyle().getClassesAsString());
		
		UIComponent uiComponent = (UIComponent) __inject__(ComponentBuilderHelper.class).build(grid.getView(ViewMap.HEADER));
		if(uiComponent!=null) {
			InputText inputText = new InputText();
			inputText.setId("globalFilter");
			inputText.setOnchange("PF('"+grid.getProperties().getWidgetVar()+"').filter();");
			inputText.setPlaceholder("Rechercher...");
			//((OutputPanel)uiComponent).getChildren().add(inputText);
			dataTable.setHeader(uiComponent);
		}
		uiComponent =  (UIComponent) __inject__(ComponentBuilderHelper.class).build(grid.getView(ViewMap.FOOTER));
		if(uiComponent!=null)
			dataTable.setFooter(uiComponent);
		
		Object dataModel = null;
		Boolean isLazyLoadable = __inject__(ValueHelper.class).defaultToIfNull(grid.getIsLazyLoadable(),Boolean.TRUE);
		dataTable.setLazy(isLazyLoadable);
		if(Boolean.TRUE.equals(isLazyLoadable)) {
			dataModel = new LazyDataModel<Object>(grid);
			dataTable.setPaginator(Boolean.TRUE);
			dataTable.setPaginatorAlwaysVisible(Boolean.FALSE);
			dataTable.setPaginatorPosition("top");
			dataTable.setRows(5);
			//dataTable.setPaginatorTemplate("{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}");
			dataTable.setRowsPerPageTemplate("5,10,15");
		}else {
			Objects objects = grid.getObjects();
			if(__inject__(CollectionHelper.class).isEmpty(objects)) {
				Rows rows = grid.getRows();
				if(__inject__(CollectionHelper.class).isNotEmpty(rows)) {
					if(objects == null)
						objects = __inject__(Objects.class);
					for(Row indexRow : rows.get()) {
						Map<String,Object> map = new LinkedHashMap<>();
						objects.add(map);
						Cells cells = indexRow.getCells();
						if(__inject__(CollectionHelper.class).isNotEmpty(cells)) {
							for(Cell indexCell : cells.get()) {
								Object value = null;
								Components components = null;
								if(indexCell.getView()!=null)
									components = indexCell.getView().getComponents();
								if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
									for(Component indexCellComponent : components.get()) {
										if(indexCellComponent instanceof OutputString) {
											value = ((OutputString)indexCellComponent).getValue();
											break;
										}
									}
								}
								map.put((String)indexCell.getColumn().getValuePropertyName(), value);
							}
						}
					}
				}
			}
			dataModel = new ListDataModel<Object>();
			((javax.faces.model.DataModel<?>)dataModel).setWrappedData(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isEmpty(objects)) ? new ArrayList<Object>() : objects.get());
		}		
		dataTable.setValue(dataModel);
		
		Columns columns = grid.getColumns();
		if(__inject__(CollectionHelper.class).isNotEmpty(columns))
			for(Column index : columns.get()) {
				org.primefaces.component.column.Column __column__ = __inject__(ColumnBuilder.class).setDataTable(dataTable).setGrid(grid).setModel(index).execute().getOutput();
				dataTable.getColumns().add(__column__);
			}
		return dataTable;
	}

}
