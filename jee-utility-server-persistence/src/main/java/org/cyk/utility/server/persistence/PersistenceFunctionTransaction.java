package org.cyk.utility.server.persistence;

import org.cyk.utility.architecture.system.SystemAction;

public interface PersistenceFunctionTransaction extends PersistenceFunction {

	Object getEntity();
	PersistenceFunctionTransaction setEntity(Object entity);
	
	PersistenceFunctionTransaction setAction(SystemAction action);
}
