package org.cyk.utility.server.persistence;

import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunction extends SystemFunctionServer {

	PersistenceFunction setAction(SystemAction action);
	
	PersistenceFunction setEntityClass(Class<?> aClass);
	
	PersistenceFunction setNamedQueryIdentifier(Object identifier);
	Object getNamedQueryIdentifier();
}
