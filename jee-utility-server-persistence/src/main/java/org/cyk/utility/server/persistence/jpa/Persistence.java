package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

public interface Persistence extends org.cyk.utility.server.persistence.Persistence{

	Persistence setEntityManager(EntityManager entityManager);
	EntityManager getEntityManager();
	
	
}
