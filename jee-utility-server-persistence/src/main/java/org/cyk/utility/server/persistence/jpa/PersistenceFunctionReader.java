package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

public interface PersistenceFunctionReader extends org.cyk.utility.server.persistence.PersistenceFunctionReader {

	EntityManager getEntityManager();
	PersistenceFunctionReader setEntityManager(EntityManager entityManager);
	
}
