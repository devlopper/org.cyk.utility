package org.cyk.utility.__kernel__.persistence;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCreator {

	@SuppressWarnings("unchecked")
	default void createMany(Collection<Object> objects,EntityManager entityManager) {
		if(entityManager == null || CollectionHelper.isEmpty(objects))
			return;
		for(Object index : objects) {			
			if(index instanceof IdentifiableSystem && FieldHelper.readSystemIdentifier(index) == null) {
				((IdentifiableSystem<String>)index).setSystemIdentifier(UUID.randomUUID().toString());
			}
			entityManager.persist(index);
		}
	}
	
	default void createMany(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		createMany(objects, DependencyInjection.inject(EntityManager.class));
	}
	
	default void createMany(EntityManager entityManager,Object...objects) {
		if(entityManager == null || ArrayHelper.isEmpty(objects))
			return;
		createMany(CollectionHelper.listOf(objects),entityManager);
	}
	
	default void createMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		createMany(CollectionHelper.listOf(objects),DependencyInjection.inject(EntityManager.class));
	}
	
	default void createOne(Object object,EntityManager entityManager) {
		if(object == null || entityManager == null)
			return;
		createMany(CollectionHelper.listOf(object),entityManager);
	}
	
	default void createOne(Object object) {
		if(object == null)
			return;
		createMany(CollectionHelper.listOf(object),DependencyInjection.inject(EntityManager.class));
	}
	
	/**/
	
	static EntityCreator getInstance() {
		EntityCreator instance = (EntityCreator) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(EntityCreator.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", EntityCreator.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
