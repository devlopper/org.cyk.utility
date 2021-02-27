package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.server.EntityManagerGetter;

public interface EntityDeletor {

	default void deleteMany(QueryExecutorArguments arguments) {
		if(arguments == null || CollectionHelper.isEmpty(arguments.getObjects()))
			return;
		EntityManager entityManager = arguments.getEntityManager();
		if(entityManager == null)
			entityManager = EntityManagerGetter.getInstance().get();
		if(Boolean.TRUE.equals(arguments.getIsTransactional()))
			entityManager.getTransaction().begin();
		for(Object index : arguments.getObjects()) {			
			entityManager.remove(entityManager.merge(index));
		}
		if(Boolean.TRUE.equals(arguments.getIsTransactional()))
			entityManager.getTransaction().commit();
	}
	
	default void deleteMany(Collection<Object> objects,EntityManager entityManager) {
		if(entityManager == null || CollectionHelper.isEmpty(objects))
			return;
		deleteMany(new QueryExecutorArguments().setObjects(objects).setEntityManager(entityManager));
	}
	
	default void deleteMany(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		deleteMany(objects, EntityManagerGetter.getInstance().get());
	}
	
	default void deleteMany(EntityManager entityManager,Object...objects) {
		if(entityManager == null || ArrayHelper.isEmpty(objects))
			return;
		deleteMany(CollectionHelper.listOf(objects),entityManager);
	}
	
	default void deleteMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		deleteMany(CollectionHelper.listOf(objects));
	}
	
	default void deleteManyInTransaction(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return;
		deleteMany(new QueryExecutorArguments().addObjects(objects).setIsTransactional(Boolean.TRUE));
	}
	
	default void deleteManyInTransaction(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		deleteMany(new QueryExecutorArguments().addObjects(CollectionHelper.listOf(objects)).setIsTransactional(Boolean.TRUE));
	}
	
	default void deleteOne(Object object,EntityManager entityManager) {
		if(object == null || entityManager == null)
			return;
		deleteMany(CollectionHelper.listOf(object),entityManager);
	}
	
	default void deleteOne(Object object) {
		if(object == null)
			return;
		deleteMany(new QueryExecutorArguments().addObjects(object));
	}
	
	default void deleteOne(Object object,Boolean isTransactional) {
		if(object == null)
			return;
		deleteMany(new QueryExecutorArguments().addObjects(object).setIsTransactional(isTransactional));
	}
	
	default void deleteOneInTransaction(Object object) {
		if(object == null)
			return;
		deleteOne(object,Boolean.TRUE);
	}
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityDeletor,Serializable {
		private static final long serialVersionUID = 1L;
	
	}
	
	/**/
	
	static EntityDeletor getInstance() {
		return Helper.getInstance(EntityDeletor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
