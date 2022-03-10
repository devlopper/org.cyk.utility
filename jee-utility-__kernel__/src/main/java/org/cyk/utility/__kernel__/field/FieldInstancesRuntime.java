package org.cyk.utility.__kernel__.field;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FieldInstancesRuntime extends Objectable {
	
	FieldInstances getInstances();
	FieldInstances getInstances(Boolean injectIfNull);
	FieldInstancesRuntime setInstances(FieldInstances instances);
	
	FieldInstance get(Class<?> aClass,Collection<String> paths);
	FieldInstance get(Class<?> aClass,String...paths);

}
