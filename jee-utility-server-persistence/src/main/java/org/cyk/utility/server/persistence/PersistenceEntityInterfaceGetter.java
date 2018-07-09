package org.cyk.utility.server.persistence;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface PersistenceEntityInterfaceGetter extends FunctionWithPropertiesAsInput<Class<?>> {

	PersistenceEntityInterfaceGetter setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
}
