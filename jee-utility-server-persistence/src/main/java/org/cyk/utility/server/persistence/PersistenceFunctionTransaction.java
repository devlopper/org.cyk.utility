package org.cyk.utility.server.persistence;

import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunctionTransaction extends PersistenceFunction {

	Object getEntity();
	PersistenceFunctionTransaction setEntity(Object entity);
	
	PersistenceFunctionTransaction setAction(SystemAction action);
}
