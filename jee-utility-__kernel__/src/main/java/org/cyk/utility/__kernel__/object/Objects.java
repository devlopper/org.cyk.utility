package org.cyk.utility.__kernel__.object;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Objects extends CollectionInstance<Object> {

	@Override Objects add(Collection<Object> collection);
	
	@Override Objects add(Object... elements);
	
}
