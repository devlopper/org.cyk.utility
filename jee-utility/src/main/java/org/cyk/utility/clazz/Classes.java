package org.cyk.utility.clazz;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

@SuppressWarnings("rawtypes")
public interface Classes extends CollectionInstance<Class> {

	@Override
	Classes add(Class... elements);
	
	@Override
	Classes add(Collection<Class> collection);
	
}
