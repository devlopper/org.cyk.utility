package org.cyk.utility.persistence.server;

import java.io.Serializable;

import javax.persistence.EntityManager;

public class EntityManagerGetterImpl extends EntityManagerGetter.AbstractImpl implements Serializable {

	public static EntityManager ENTITY_MANAGER;
	
	@Override
	public EntityManager get() {
		if(ENTITY_MANAGER == null)
			return super.get();
		return ENTITY_MANAGER;
	}
	
}
