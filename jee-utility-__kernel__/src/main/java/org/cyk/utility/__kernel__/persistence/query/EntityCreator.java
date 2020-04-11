package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCreator {

	default void createMany(QueryExecutorArguments arguments) {
		if(arguments == null || CollectionHelper.isEmpty(arguments.getObjects()))
			return;
		EntityManager entityManager = arguments.getEntityManager();
		if(entityManager == null)
			entityManager = EntityManagerGetter.getInstance().get();
		if(Boolean.TRUE.equals(arguments.getIsTransactional()))
			entityManager.getTransaction().begin();
		for(Object index : arguments.getObjects()) {			
			if(index instanceof IdentifiableSystem && FieldHelper.readSystemIdentifier(index) == null) {
				((IdentifiableSystem<String>)index).setSystemIdentifier(UUID.randomUUID().toString());
			}
			entityManager.persist(index);
		}
		if(Boolean.TRUE.equals(arguments.getIsTransactional()))
			entityManager.getTransaction().commit();
	}
	
	default void createMany(Collection<Object> objects,EntityManager entityManager) {
		if(entityManager == null || CollectionHelper.isEmpty(objects))
			return;
		createMany(new QueryExecutorArguments().setObjects(objects).setEntityManager(entityManager));
	}
	
	default void createMany(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		createMany(objects, EntityManagerGetter.getInstance().get());
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
	
	default void createManyInTransaction(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		createMany(new QueryExecutorArguments().addObjects(objects).setIsTransactional(Boolean.TRUE));
	}
	
	default void createManyInTransaction(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		createMany(new QueryExecutorArguments().addObjects(CollectionHelper.listOf(objects)).setIsTransactional(Boolean.TRUE));
	}
	
	default void createOne(Object object,EntityManager entityManager) {
		if(object == null || entityManager == null)
			return;
		createMany(CollectionHelper.listOf(object),entityManager);
	}
	
	default void createOne(Object object) {
		if(object == null)
			return;
		createMany(new QueryExecutorArguments().addObjects(object));
	}
	
	default void createOne(Object object,Boolean isTransactional) {
		if(object == null)
			return;
		createMany(new QueryExecutorArguments().addObjects(object).setIsTransactional(isTransactional));
	}
	
	default void createOneInTransaction(Object object) {
		if(object == null)
			return;
		createOne(object,Boolean.TRUE);
	}
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityCreator,Serializable {
		private static final long serialVersionUID = 1L;
	
	}
	
	/**/
	
	static EntityCreator getInstance() {
		return Helper.getInstance(EntityCreator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
