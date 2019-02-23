package org.cyk.utility.clazz;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface ClassInstancesRuntime extends Objectable {

	ClassInstances getInstances();
	ClassInstances getInstances(Boolean injectIfNull);
	ClassInstancesRuntime setInstances(ClassInstances instances);
	
	ClassInstance get(Class<?> aClass);
}
