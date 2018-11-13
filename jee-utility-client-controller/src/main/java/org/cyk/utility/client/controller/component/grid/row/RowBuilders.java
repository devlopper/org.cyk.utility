package org.cyk.utility.client.controller.component.grid.row;

import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.collection.CollectionInstance;

public interface RowBuilders extends CollectionInstance<RowBuilder> {

	Class<? extends Row> getRowClass();
	RowBuilders setRowClass(Class<? extends Row> rowClass);
	
}
