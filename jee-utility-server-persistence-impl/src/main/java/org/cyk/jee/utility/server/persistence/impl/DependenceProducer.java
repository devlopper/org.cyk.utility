package org.cyk.jee.utility.server.persistence.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface DependenceProducer {

	EntityManagerFactory getEntityManagerFactory();
	
	EntityManager getEntityManager();
	
}
