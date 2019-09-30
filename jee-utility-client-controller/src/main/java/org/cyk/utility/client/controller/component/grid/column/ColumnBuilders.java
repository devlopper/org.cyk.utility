package org.cyk.utility.client.controller.component.grid.column;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface ColumnBuilders extends CollectionInstance<ColumnBuilder> {

	ColumnBuilder getByFieldNameStrings(Collection<String> fieldNameStrings);
	ColumnBuilder getByFieldNameStrings(String...fieldNameStrings);
	
}
