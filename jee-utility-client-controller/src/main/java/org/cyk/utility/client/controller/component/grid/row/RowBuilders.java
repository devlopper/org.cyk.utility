package org.cyk.utility.client.controller.component.grid.row;

import java.util.Collection;

import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.client.controller.data.RowListener;
import org.cyk.utility.client.controller.data.RowListeners;
import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface RowBuilders extends CollectionInstance<RowBuilder> {

	Class<? extends Row> getRowClass();
	RowBuilders setRowClass(Class<? extends Row> rowClass);
	
	RowListeners getRowListeners();
	RowListeners getRowListeners(Boolean injectIfNull);
	RowBuilders setRowListeners(RowListeners rowListeners);
	RowBuilders addRowListeners(Collection<RowListener> rowListeners);
	RowBuilders addRowListeners(RowListener...rowListeners);
	
}
