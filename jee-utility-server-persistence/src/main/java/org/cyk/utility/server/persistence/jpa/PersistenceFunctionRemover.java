package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

public interface PersistenceFunctionRemover extends org.cyk.utility.server.persistence.PersistenceFunctionRemover {

	EntityManager getEntityManager();
	PersistenceFunctionRemover setEntityManager(EntityManager entityManager);
	
}
