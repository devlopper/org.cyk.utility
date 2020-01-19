package org.cyk.utility.__kernel__.persistence.query.filter;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Fields extends CollectionInstance<Field> {

	Field getByPath(String...paths);	
	Boolean hasPath(String...paths);
	
	
}
