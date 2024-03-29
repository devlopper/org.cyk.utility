package org.cyk.utility.client.controller.component.grid;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.grid.row.Rows;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.__kernel__.object.Objects;

public interface Grid extends VisibleComponent {

	Columns getColumns();
	Grid setColumns(Columns columns);
	
	Rows getRows();
	Grid setRows(Rows rows);
	
	Cell getCell(Column column,Row row);
	
	View getView();
	Grid setView(View view);
	
	ViewMap getViewMap();
	Grid setViewMap(ViewMap viewMap);
	View getView(String key);
	
	Objects getObjects();
	Objects getObjects(Boolean injectIfNull);
	Grid setObjects(Objects objects);
	
	Boolean getIsLazyLoadable();
	Grid setIsLazyLoadable(Boolean isLazyLoadable);
	
}
