package org.cyk.utility.client.controller.component.grid.column;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;

public interface ColumnBuilders extends CollectionInstance<ColumnBuilder> {

	ColumnBuilder getByFieldNameStrings(Collection<String> fieldNameStrings);
	ColumnBuilder getByFieldNameStrings(String...fieldNameStrings);
	
}
