package org.cyk.utility.client.controller.component.grid.cell;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.view.View;

public class CellImpl extends AbstractVisibleComponentImpl implements Cell,Serializable {
	private static final long serialVersionUID = 1L;

	private View view;
	private Column column;
	private Row row;
	
	@Override
	public View getView() {
		return view;
	}

	@Override
	public Cell setView(View view) {
		this.view = view;
		return this;
	}

	@Override
	public Column getColumn() {
		return column;
	}

	@Override
	public Cell setColumn(Column column) {
		this.column = column;
		return this;
	}

	@Override
	public Row getRow() {
		return row;
	}

	@Override
	public Cell setRow(Row row) {
		this.row = row;
		return this;
	}

}
