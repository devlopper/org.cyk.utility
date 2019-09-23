package org.cyk.utility.field;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface FieldInstances extends CollectionInstance<FieldInstance> {

	FieldInstance get(Class<?> aClass,Collection<String> paths);
	FieldInstance get(Class<?> aClass,String...paths);
	
}
