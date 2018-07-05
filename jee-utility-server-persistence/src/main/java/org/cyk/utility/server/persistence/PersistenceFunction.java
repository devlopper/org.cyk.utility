package org.cyk.utility.server.persistence;

import javax.persistence.EntityManager;

import org.cyk.utility.architecture.system.SystemAction;
import org.cyk.utility.architecture.system.SystemFunction;

public interface PersistenceFunction extends SystemFunction {

	PersistenceFunction setAction(SystemAction action);
	
	EntityManager getEntityManager();
	PersistenceFunction setEntityManager(EntityManager entityManager);
	
}
