package org.cyk.utility.persistence.server;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Deprecated
public class EntityManagerFactoryProducerImpl extends EntityManagerFactoryProducer.AbstractImpl implements EntityManagerFactoryProducer,Serializable {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Override //@Produces
	public EntityManagerFactory produce() {
		return entityManagerFactory;
	}
}