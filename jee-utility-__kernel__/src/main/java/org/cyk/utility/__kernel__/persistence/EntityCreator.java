package org.cyk.utility.__kernel__.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EntityCreator {

	@SuppressWarnings("unchecked")
	default void createMany(Arguments arguments) {
		if(arguments == null || CollectionHelper.isEmpty(arguments.objects))
			return;
		EntityManager entityManager = arguments.entityManager;
		if(entityManager == null)
			entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
		if(Boolean.TRUE.equals(arguments.isTransactional))
			entityManager.getTransaction().begin();
		for(Object index : arguments.objects) {			
			if(index instanceof IdentifiableSystem && FieldHelper.readSystemIdentifier(index) == null) {
				((IdentifiableSystem<String>)index).setSystemIdentifier(UUID.randomUUID().toString());
			}
			entityManager.persist(index);
		}
		if(Boolean.TRUE.equals(arguments.isTransactional))
			entityManager.getTransaction().commit();
	}
	
	default void createMany(Collection<Object> objects,EntityManager entityManager) {
		if(entityManager == null || CollectionHelper.isEmpty(objects))
			return;
		createMany(new Arguments().setObjects(objects).setEntityManager(entityManager));
	}
	
	default void createMany(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		createMany(objects, DependencyInjection.inject(EntityManagerGetter.class).get());
	}
	
	default void createMany(EntityManager entityManager,Object...objects) {
		if(entityManager == null || ArrayHelper.isEmpty(objects))
			return;
		createMany(CollectionHelper.listOf(objects),entityManager);
	}
	
	default void createMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		createMany(CollectionHelper.listOf(objects));
	}
	
	default void createOne(Object object,EntityManager entityManager) {
		if(object == null || entityManager == null)
			return;
		createMany(CollectionHelper.listOf(object),entityManager);
	}
	
	default void createOne(Object object) {
		if(object == null)
			return;
		createMany(new Arguments().addObjects(object));
	}
	
	default void createOne(Object object,Boolean isTransactional) {
		if(object == null)
			return;
		createMany(new Arguments().addObjects(object).setIsTransactional(isTransactional));
	}
	
	default void createOneInTransaction(Object object) {
		if(object == null)
			return;
		createOne(object,Boolean.TRUE);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Collection<Object> objects;
		private EntityManager entityManager;
		private Boolean isTransactional;
		
		public Collection<Object> getObjects(Boolean injectIfNull) {
			if(objects == null && Boolean.TRUE.equals(injectIfNull))
				objects = new ArrayList<>();
			return objects;
		}
		
		public Arguments addObjects(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return this;
			getObjects(Boolean.TRUE).addAll(objects);
			return this;
		}
		
		public Arguments addObjects(Object...objects) {
			if(ArrayHelper.isEmpty(objects))
				return this;
			addObjects(CollectionHelper.listOf(objects));
			return this;
		}
	}
	
	/**/
	
	static EntityCreator getInstance() {
		return Helper.getInstance(EntityCreator.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
