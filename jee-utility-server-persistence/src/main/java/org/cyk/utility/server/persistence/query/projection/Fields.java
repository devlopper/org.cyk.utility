package org.cyk.utility.server.persistence.query.projection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

@Deprecated
public interface Fields extends CollectionInstance<Field> {

	Field getByPath(String...paths);	
	Boolean hasPath(String...paths);
	
	
}
