package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class DependenceProducerImpl implements Serializable,DependenceProducer {
	private static final long serialVersionUID = 1L;

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override @Produces
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	@Override @Produces
	public EntityManager getEntityManager() {
		return entityManager;
	}

	
	
}
