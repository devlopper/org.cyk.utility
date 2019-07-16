package org.cyk.utility.object;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;

public interface Objects extends CollectionInstance<Object> {

	@Override Objects add(Collection<Object> collection);
	
	@Override Objects add(Object... elements);
	
}
