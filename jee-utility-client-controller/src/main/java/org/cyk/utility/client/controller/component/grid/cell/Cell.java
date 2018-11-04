package org.cyk.utility.client.controller.component.grid.cell;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.view.View;

public interface Cell extends VisibleComponent {
	
	Column getColumn();
	Cell setColumn(Column column);
	
	Row getRow();
	Cell setRow(Row row);

	View getView();
	Cell setView(View view);
	
}
