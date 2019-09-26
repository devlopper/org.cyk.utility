package org.cyk.utility.__kernel__.field;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.field.FieldInstance;

public interface FieldInstances extends CollectionInstance<FieldInstance> {

	FieldInstance get(Class<?> aClass,Collection<String> paths);
	FieldInstance get(Class<?> aClass,String...paths);
	
}
