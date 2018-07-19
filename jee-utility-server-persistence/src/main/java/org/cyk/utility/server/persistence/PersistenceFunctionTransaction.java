package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunctionTransaction extends PersistenceFunction {

	PersistenceFunctionTransaction setEntity(Object entity);
	
	PersistenceFunctionTransaction setEntities(Collection<?> entities);
	
	PersistenceFunctionTransaction setAction(SystemAction action);
}
