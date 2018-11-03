package org.cyk.utility.client.controller.component.grid.row;

import org.cyk.utility.client.controller.component.grid.Dimension;
import org.cyk.utility.client.controller.component.grid.cell.Cells;

public interface Row extends Dimension {

	Cells getCells();
	Row setCells(Cells cells);
	
}
