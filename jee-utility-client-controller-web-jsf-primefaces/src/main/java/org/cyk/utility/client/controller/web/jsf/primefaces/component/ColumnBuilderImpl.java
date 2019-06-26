package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.StringHelper;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;

public class ColumnBuilderImpl extends AbstractUIComponentBuilderImpl<Column,org.cyk.utility.client.controller.component.grid.column.Column> implements ColumnBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	private Grid grid;
	
	@Override
	protected Column __execute__(org.cyk.utility.client.controller.component.grid.column.Column column, ValueExpressionMap valueExpressionMap) throws Exception {
		DataTable dataTable = getDataTable();
		Grid grid = getGrid();
		
		org.primefaces.component.column.Column __column__ = new org.primefaces.component.column.Column();
		if(column.getWidth() != null)
			__column__.setWidth(column.getWidth().toString());
		
		UIComponent uiComponent =  (UIComponent) __inject__(ComponentBuilderHelper.class).build(column.getView(ViewMap.HEADER)); //__build__(column.getView(ViewMap.HEADER),column,dataTable);
		if(uiComponent!=null)
			__column__.setHeader(uiComponent);
		
		uiComponent = (UIComponent) __inject__(ComponentBuilderHelper.class).build(column.getView(ViewMap.BODY));
		String valuePropertyName = __inject__(CollectionHelper.class).isEmpty(grid.getObjects()) ? column.getValuePropertyName() : column.getFieldName();
		if(uiComponent==null) {
			if(__inject__(StringHelper.class).isNotBlank(valuePropertyName)) {
				uiComponent = new HtmlOutputText();
				//__setValueExpression__(htmlOutputText, "value", __buildValueExpressionString__(__formatExpression__(dataTable.getVar()+"['"+valuePropertyName+"']")));
				__setValueExpression__(uiComponent, "value", __buildValueExpressionString__(__formatExpression__(dataTable.getVar()+"."+valuePropertyName)));
				
			}else {
				
			}
		}else {
			UIComponent child = __inject__(CollectionHelper.class).getFirst(uiComponent.getChildren());
			if(child instanceof HtmlOutputText || child instanceof HtmlInputText)
				__setValueExpression__(child, "value", __buildValueExpressionString__(__formatExpression__(dataTable.getVar()+"."+valuePropertyName)));
		}
		if(uiComponent!=null)
			__column__.getChildren().add(uiComponent);
		
		uiComponent = (UIComponent) __inject__(ComponentBuilderHelper.class).build(column.getView(ViewMap.FOOTER));
		if(uiComponent!=null)
			__column__.setFooter(uiComponent);
		
		return __column__;
	}
	
	@Override
	public DataTable getDataTable() {
		return dataTable;
	}
	
	@Override
	public ColumnBuilder setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
		return this;
	}
	
	@Override
	public Grid getGrid() {
		return grid;
	}
	
	@Override
	public ColumnBuilder setGrid(Grid grid) {
		this.grid = grid;
		return this;
	}
	
}
