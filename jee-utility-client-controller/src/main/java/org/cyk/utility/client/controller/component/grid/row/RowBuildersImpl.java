package org.cyk.utility.client.controller.component.grid.row;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.client.controller.data.RowListener;
import org.cyk.utility.client.controller.data.RowListeners;
import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class RowBuildersImpl extends AbstractCollectionInstanceImpl<RowBuilder> implements RowBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<? extends Row> rowClass;
	private RowListeners rowListeners;
	
	@Override
	public Class<? extends Row> getRowClass() {
		return rowClass;
	}

	@Override
	public RowBuilders setRowClass(Class<? extends Row> rowClass) {
		this.rowClass = rowClass;
		return this;
	}
	
	@Override
	public RowListeners getRowListeners() {
		return rowListeners;
	}
	
	@Override
	public RowListeners getRowListeners(Boolean injectIfNull) {
		return (RowListeners) __getInjectIfNull__(FIELD_ROW_LISTENERS, injectIfNull);
	}
	
	@Override
	public RowBuilders setRowListeners(RowListeners rowListeners) {
		this.rowListeners = rowListeners;
		return this;
	}
	
	@Override
	public RowBuilders addRowListeners(Collection<RowListener> rowListeners) {
		getRowListeners(Boolean.TRUE).add(rowListeners);
		return this;
	}
	
	@Override
	public RowBuilders addRowListeners(RowListener... rowListeners) {
		getRowListeners(Boolean.TRUE).add(rowListeners);
		return this;
	}
	
	public static final String FIELD_ROW_LISTENERS = "rowListeners";

}
