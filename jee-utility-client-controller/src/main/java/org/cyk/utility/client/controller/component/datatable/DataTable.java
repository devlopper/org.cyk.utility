package org.cyk.utility.client.controller.component.datatable;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.column.Columns;

public interface DataTable extends VisibleComponent {

	Columns getColumns();
	DataTable setColumns(Columns columns);
	
}
