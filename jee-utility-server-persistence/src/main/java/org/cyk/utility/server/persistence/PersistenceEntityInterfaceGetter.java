package org.cyk.utility.server.persistence;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@SuppressWarnings("rawtypes") // <?> has been removed to solve WELD-001125: Illegal bean type
public interface PersistenceEntityInterfaceGetter extends FunctionWithPropertiesAsInput<Class> {

	PersistenceEntityInterfaceGetter setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
}
