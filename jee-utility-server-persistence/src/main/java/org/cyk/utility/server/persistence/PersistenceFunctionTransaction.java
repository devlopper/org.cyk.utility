package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunctionTransaction extends PersistenceFunction {

	Object getEntity();
	PersistenceFunctionTransaction setEntity(Object entity);
	
	Collection<?> getEntities();
	PersistenceFunctionTransaction setEntities(Collection<?> entities);
	
	PersistenceFunctionTransaction setAction(SystemAction action);
}
