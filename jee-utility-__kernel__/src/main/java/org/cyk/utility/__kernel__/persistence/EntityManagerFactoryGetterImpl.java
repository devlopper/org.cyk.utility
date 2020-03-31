package org.cyk.utility.__kernel__.persistence;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;

public class EntityManagerFactoryGetterImpl extends EntityManagerFactoryGetter.AbstractImpl implements Serializable {

	public static EntityManagerFactory ENTITY_MANAGER_FACTORY; 
	
	@Override
	public EntityManagerFactory get() {
		return ENTITY_MANAGER_FACTORY;
	}

}
