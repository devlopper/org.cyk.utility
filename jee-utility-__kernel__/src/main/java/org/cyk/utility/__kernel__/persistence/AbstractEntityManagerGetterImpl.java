package org.cyk.utility.__kernel__.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;

public abstract class AbstractEntityManagerGetterImpl extends AbstractObject implements EntityManagerGetter,Serializable {

	@Override
	public EntityManager get() {
		EntityManagerFactory entityManagerFactory = __inject__(EntityManagerFactoryGetter.class).get();
		if(entityManagerFactory == null)
			throw new RuntimeException("Entity manager factory cannot bet got.");
		return entityManagerFactory.createEntityManager();
	}

	
}
