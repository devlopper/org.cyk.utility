package org.cyk.utility.client.controller.component.grid.row;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.grid.AbstractDimensionBuilderImpl;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilder;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilders;
import org.cyk.utility.client.controller.component.grid.cell.Cells;

public class RowBuilderImpl extends AbstractDimensionBuilderImpl<Row> implements RowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CellBuilders cells;
	
	@Override
	protected void __execute__(Row row) {
		super.__execute__(row);
		CellBuilders cells = getCells();
		if(__injectCollectionHelper__().isNotEmpty(cells)) {
			row.setCells(__inject__(Cells.class));
			Integer cellOrderNumber = 0;
			for(CellBuilder index : cells.get()) {
				Cell cell = index.execute().getOutput().setRow(row);
				cell.setOrderNumber(cellOrderNumber++);
				row.getCells().add(cell);
			}
		}
	}
	
	@Override
	public CellBuilders getCells() {
		return cells;
	}

	@Override
	public CellBuilders getCells(Boolean injectIfNull) {
		return (CellBuilders) __getInjectIfNull__(FIELD_CELLS, injectIfNull);
	}

	@Override
	public RowBuilder setCells(CellBuilders cells) {
		this.cells = cells;
		return this;
	}

	@Override
	public RowBuilder addCells(Collection<CellBuilder> cells) {
		getCells(Boolean.TRUE).add(cells);
		return this;
	}

	@Override
	public RowBuilder addCells(CellBuilder... cells) {
		getCells(Boolean.TRUE).add(cells);
		return this;
	}

	/**/
	
	public static final String FIELD_CELLS = "cells";
}
