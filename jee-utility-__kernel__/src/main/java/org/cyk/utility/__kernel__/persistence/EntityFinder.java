package org.cyk.utility.__kernel__.persistence;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityFinder {

	default <T> T find(Class<T> entityClass,Object identifier,EntityManager entityManager) {
		if(entityClass == null || identifier == null || entityManager == null)
			return null;
		return entityManager.find(entityClass, identifier);
	}
	
	default <T> T find(Class<T> entityClass,Object identifier) {
		if(entityClass == null || identifier == null)
			return null;
		return find(entityClass, identifier,EntityManagerGetter.getInstance().get());
	}
	
	/**/
	
	static EntityFinder getInstance() {
		return Helper.getInstance(EntityFinder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
