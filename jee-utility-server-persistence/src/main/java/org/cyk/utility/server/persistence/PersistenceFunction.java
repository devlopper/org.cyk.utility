package org.cyk.utility.server.persistence;

import javax.persistence.EntityManager;

import org.cyk.utility.system.SystemFunction;
import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunction extends SystemFunction {

	PersistenceFunction setAction(SystemAction action);
	
	EntityManager getEntityManager();
	PersistenceFunction setEntityManager(EntityManager entityManager);
	
	PersistenceFunction setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
}
