package org.cyk.utility.clazz;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface ClassInstances extends CollectionInstance<ClassInstance> {

	ClassInstance get(Class<?> aClass);
	
}
