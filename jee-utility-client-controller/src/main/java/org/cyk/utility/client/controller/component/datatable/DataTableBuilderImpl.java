package org.cyk.utility.client.controller.component.datatable;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.column.ColumnBuilders;
import org.cyk.utility.client.controller.component.column.Columns;

public class DataTableBuilderImpl extends AbstractVisibleComponentBuilderImpl<DataTable> implements DataTableBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ColumnBuilders columns;
	
	@Override
	protected void __execute__(DataTable dataTable) {
		super.__execute__(dataTable);
		ColumnBuilders columns = getColumns();
		if(columns!=null) {
			dataTable.setColumns(__inject__(Columns.class));
			for(ColumnBuilder index : columns.get()) {
				dataTable.getColumns().add(index.execute().getOutput());
			}
		}
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
	public DataTableBuilder setColumns(ColumnBuilders columns) {
		this.columns = columns;
		return this;
	}
	
	@Override
	public DataTableBuilder addColumns(Collection<ColumnBuilder> columns) {
		getColumns(Boolean.TRUE).add(columns);
		return this;
	}
	
	@Override
	public DataTableBuilder addColumns(ColumnBuilder...columns) {
		getColumns(Boolean.TRUE).add(columns);
		return this;
	}
	
	public static final String FIELD_COLUMNS = "columns";

}
