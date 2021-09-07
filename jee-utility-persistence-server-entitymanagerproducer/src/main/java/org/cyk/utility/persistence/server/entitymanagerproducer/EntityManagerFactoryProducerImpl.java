package org.cyk.utility.persistence.server.entitymanagerproducer;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class EntityManagerFactoryProducerImpl extends EntityManagerFactoryProducer.AbstractImpl implements EntityManagerFactoryProducer,Serializable {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Override @Produces
	public EntityManagerFactory produce() {
		return entityManagerFactory;
	}
}