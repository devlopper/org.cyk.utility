package org.cyk.utility.server.persistence.query.projection;

import org.cyk.utility.collection.CollectionInstance;

public interface Fields extends CollectionInstance<Field> {

	Field getByPath(String...paths);	
	Boolean hasPath(String...paths);
	
	
}
