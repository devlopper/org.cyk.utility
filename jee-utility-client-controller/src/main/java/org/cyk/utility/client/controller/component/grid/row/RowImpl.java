package org.cyk.utility.client.controller.component.grid.row;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.AbstractDimensionImpl;
import org.cyk.utility.client.controller.component.grid.cell.Cells;

public class RowImpl extends AbstractDimensionImpl implements Row,Serializable {
	private static final long serialVersionUID = 1L;

	private Cells cells;
	
	@Override
	public Cells getCells() {
		return cells;
	}

	@Override
	public Row setCells(Cells cells) {
		this.cells = cells;
		return this;
	}

}
