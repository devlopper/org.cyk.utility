package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

public interface PersistenceFunctionModifier extends org.cyk.utility.server.persistence.PersistenceFunctionModifier {

	EntityManager getEntityManager();
	PersistenceFunctionModifier setEntityManager(EntityManager entityManager);
	
}
