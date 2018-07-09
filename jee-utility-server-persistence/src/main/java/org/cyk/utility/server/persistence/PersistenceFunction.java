package org.cyk.utility.server.persistence;

import org.cyk.utility.system.SystemFunction;
import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunction extends SystemFunction {

	PersistenceFunction setAction(SystemAction action);
	
	PersistenceFunction setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
}
