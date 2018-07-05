package org.cyk.utility.server.persistence;

import org.cyk.utility.architecture.system.SystemAction;

public interface PersistenceFunctionRead extends PersistenceFunction {

	PersistenceFunctionRead setAction(SystemAction action);
	
	PersistenceFunctionRead setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
	PersistenceFunctionRead setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
}
