package org.cyk.utility.persistence.server;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Deprecated
public class EntityManagerProducerImpl extends EntityManagerProducer.AbstractImpl implements Serializable {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override //@Produces
	public EntityManager produce() {
		return entityManager;
	}
}