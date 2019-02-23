package org.cyk.utility.clazz;

import org.cyk.utility.collection.CollectionInstance;

public interface ClassInstances extends CollectionInstance<ClassInstance> {

	ClassInstance get(Class<?> aClass);
	
}
