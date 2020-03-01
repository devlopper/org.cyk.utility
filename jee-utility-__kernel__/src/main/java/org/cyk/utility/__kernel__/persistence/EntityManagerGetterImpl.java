package org.cyk.utility.__kernel__.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;

public class EntityManagerGetterImpl extends AbstractEntityManagerGetterImpl implements Serializable {

	public static EntityManager ENTITY_MANAGER;
	
	@Override
	public EntityManager get() {
		if(ENTITY_MANAGER == null)
			return super.get();
		return ENTITY_MANAGER;
	}
	
}
