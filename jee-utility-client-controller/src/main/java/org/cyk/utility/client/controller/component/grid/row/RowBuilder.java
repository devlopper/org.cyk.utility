package org.cyk.utility.client.controller.component.grid.row;

import java.util.Collection;

import org.cyk.utility.client.controller.component.grid.DimensionBuilder;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilder;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilders;

public interface RowBuilder extends DimensionBuilder<Row> {

	CellBuilders getCells();
	CellBuilders getCells(Boolean injectIfNull);
	RowBuilder setCells(CellBuilders cells);
	RowBuilder addCells(Collection<CellBuilder> cells);
	RowBuilder addCells(CellBuilder...cells);
}
