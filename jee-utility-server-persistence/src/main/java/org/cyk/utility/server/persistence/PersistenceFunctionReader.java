package org.cyk.utility.server.persistence;

import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunctionReader extends PersistenceFunction {

	PersistenceFunctionReader setAction(SystemAction action);
	
	PersistenceFunctionReader setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
	PersistenceFunctionReader setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
	
	/**/
	
	String NOT_FOUND = "not found";
}
