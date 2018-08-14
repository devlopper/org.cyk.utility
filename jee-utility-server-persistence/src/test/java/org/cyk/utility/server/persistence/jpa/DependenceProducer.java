package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface DependenceProducer {

	EntityManagerFactory getEntityManagerFactory();
	
	EntityManager getEntityManager();
	
}
