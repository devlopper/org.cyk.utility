package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.object.ObjectByClassMap;
import org.cyk.utility.object.Objects;

public class RowBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Row> implements RowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Data data;
	private Class<? extends Data> dataClass;
	private GridBuilder grid;
	private Objects rows;
	//private Class<?> clazz;
	
	@Override
	protected Row __execute__() throws Exception {
		Row row = null;
		Class<? extends Data> dataClass = getDataClass();
		Data data = getData();
		GridBuilder grid = getGrid();
		
		ObjectByClassMap navigationsParametersMap = null;
		if(navigationsParametersMap == null) {
			if(grid!=null)
				navigationsParametersMap = grid.getCommandablesColumnCommandablesNavigationsParametersMap();
		}
		
		Class<? extends org.cyk.utility.client.controller.data.Row> rowClass = null;
		if(rowClass == null) {
			if(grid!=null && grid.getRows()!=null)
				rowClass = grid.getRows().getRowClass();	
		}
		
		if(rowClass == null)
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : Row class is required");
		
		if(rowClass!=null) {
			row = __inject__(rowClass);
			if(row instanceof org.cyk.utility.client.controller.data.Row) {
				((org.cyk.utility.client.controller.data.Row)row).setListeners(grid.getRows().getRowListeners());
				((org.cyk.utility.client.controller.data.Row)row).setNavigationParametersMap(navigationsParametersMap);
			}
			if(row instanceof org.cyk.utility.client.controller.data.RowData) {
				((org.cyk.utility.client.controller.data.RowData<Data>)row).setData(data);
				((org.cyk.utility.client.controller.data.RowData<Data>)row).setDataClass((Class<Data>) dataClass);
			}
		}
		
		Objects rows = getRows();
		if(rows == null) {
			if(grid!=null)
				rows = getGrid().getComponent().getObjects();
		}
		
		row.setOrderNumber(__inject__(CollectionHelper.class).getSize(rows)+1);
		
		return row;
	}
	
	@Override
	public RowBuilder setData(Data data) {
		this.data = data;
		return this;
	}

	@Override
	public Data getData() {
		return data;
	}
	
	@Override
	public RowBuilder setGrid(GridBuilder grid) {
		this.grid = grid;
		return this;
	}

	@Override
	public GridBuilder getGrid() {
		return grid;
	}
	
	@Override
	public Objects getRows() {
		return rows;
	}
	@Override
	public RowBuilder setRows(Objects rows) {
		this.rows = rows;
		return this;
	}
	
	@Override
	public RowBuilder setDataClass(Class<? extends Data> dataClass) {
		this.dataClass = dataClass;
		return this;
	}
	
	@Override
	public Class<? extends Data> getDataClass() {
		return dataClass;
	}
	
	/*
	@Override
	public RowBuilder setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return clazz;
	}
	*/
	

}
