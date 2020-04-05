package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityUpdater {

	default void updateMany(QueryExecutorArguments arguments) {
		if(arguments == null || CollectionHelper.isEmpty(arguments.getObjects()))
			return;
		EntityManager entityManager = arguments.getEntityManager();
		if(entityManager == null)
			entityManager = EntityManagerGetter.getInstance().get();
		if(Boolean.TRUE.equals(arguments.getIsTransactional()))
			entityManager.getTransaction().begin();
		for(Object index : arguments.getObjects()) {
			entityManager.merge(index);
		}
		if(Boolean.TRUE.equals(arguments.getIsTransactional()))
			entityManager.getTransaction().commit();
	}
	
	default void updateMany(Collection<Object> objects,EntityManager entityManager) {
		if(entityManager == null || CollectionHelper.isEmpty(objects))
			return;
		updateMany(new QueryExecutorArguments().setObjects(objects).setEntityManager(entityManager));
	}
	
	default void updateMany(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		updateMany(objects, EntityManagerGetter.getInstance().get());
	}
	
	default void updateMany(EntityManager entityManager,Object...objects) {
		if(entityManager == null || ArrayHelper.isEmpty(objects))
			return;
		updateMany(CollectionHelper.listOf(objects),entityManager);
	}
	
	default void updateMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		updateMany(CollectionHelper.listOf(objects));
	}
	
	default void updateManyInTransaction(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		updateMany(new QueryExecutorArguments().addObjects(objects).setIsTransactional(Boolean.TRUE));
	}
	
	default void updateManyInTransaction(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		updateMany(new QueryExecutorArguments().addObjects(CollectionHelper.listOf(objects)).setIsTransactional(Boolean.TRUE));
	}
	
	default void updateOne(Object object,EntityManager entityManager) {
		if(object == null || entityManager == null)
			return;
		updateMany(CollectionHelper.listOf(object),entityManager);
	}
	
	default void updateOne(Object object) {
		if(object == null)
			return;
		updateMany(new QueryExecutorArguments().addObjects(object));
	}
	
	default void updateOne(Object object,Boolean isTransactional) {
		if(object == null)
			return;
		updateMany(new QueryExecutorArguments().addObjects(object).setIsTransactional(isTransactional));
	}
	
	default void updateOneInTransaction(Object object) {
		if(object == null)
			return;
		updateOne(object,Boolean.TRUE);
	}
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityUpdater,Serializable {
		private static final long serialVersionUID = 1L;
	
	}
	
	/**/
	
	static EntityUpdater getInstance() {
		return Helper.getInstance(EntityUpdater.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}