package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;
@Deprecated
public interface PersistenceFunctionCreator extends org.cyk.utility.server.persistence.PersistenceFunctionCreator {

	EntityManager getEntityManager();
	PersistenceFunctionCreator setEntityManager(EntityManager entityManager);
	
}
