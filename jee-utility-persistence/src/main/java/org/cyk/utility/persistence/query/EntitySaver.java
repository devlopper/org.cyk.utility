package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.EntityManagerGetter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EntitySaver {

	<T> void save(Class<T> tupleClass,Arguments<T> arguments);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntitySaver,Serializable {
		
		@Override
		public <T> void save(Class<T> tupleClass,Arguments<T> arguments) {
			validatePreConditions(tupleClass,arguments);
			arguments.computeExistingIdentifiers();
			arguments.computeCreatables();
			arguments.computeUpdatables();
			arguments.computeDeletables();
			EntityManager entityManager = arguments.getEntityManager();
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			if(Boolean.TRUE.equals(arguments.isTransactional))
				entityManager.getTransaction().begin();
			if(CollectionHelper.isNotEmpty(arguments.__creatables__))
				create(tupleClass,arguments.__creatables__, arguments.listener, entityManager);
			if(CollectionHelper.isNotEmpty(arguments.__updatables__))
				update(tupleClass,arguments.__updatables__, arguments.listener, entityManager);
			if(CollectionHelper.isNotEmpty(arguments.__deletables__))
				delete(tupleClass,arguments.__deletables__, arguments.listener, entityManager);
			/*
			if(arguments.listener == null) {
				if(CollectionHelper.isNotEmpty(arguments.__creatables__))
					Listener.AbstractImpl.__create__(arguments.__creatables__, entityManager);
				if(CollectionHelper.isNotEmpty(arguments.__updatables__))
					Listener.AbstractImpl.__update__(arguments.__updatables__, entityManager);
				if(CollectionHelper.isNotEmpty(arguments.__deletables__))
					Listener.AbstractImpl.__delete__(arguments.__deletables__, entityManager);
			}else {
				if(CollectionHelper.isNotEmpty(arguments.__creatables__))
					arguments.listener.create(arguments.__creatables__,entityManager);
				if(CollectionHelper.isNotEmpty(arguments.__updatables__))
					arguments.listener.update(arguments.__updatables__,entityManager);
				if(CollectionHelper.isNotEmpty(arguments.__deletables__))
					arguments.listener.delete(arguments.__deletables__,entityManager);
			}
			*/
			if(Boolean.TRUE.equals(arguments.isTransactional))
				entityManager.getTransaction().commit();
		}
		
		/**/
		
		protected <T> void create(Class<T> tupleClass,Collection<T> collection,Listener<T> listener,EntityManager entityManager) {
			if(CollectionHelper.isEmpty(collection))
				return;
			if(listener == null)
				Listener.AbstractImpl.__create__(collection, entityManager);
			else
				listener.create(collection,entityManager);			
		}
		
		protected <T> void update(Class<T> tupleClass,Collection<T> collection,Listener<T> listener,EntityManager entityManager) {
			if(listener == null)
				Listener.AbstractImpl.__update__(collection, entityManager);
			else
				listener.update(collection,entityManager);			
		}
		
		protected <T> void delete(Class<T> tupleClass,Collection<T> collection,Listener<T> listener,EntityManager entityManager) {
			if(listener == null)
				Listener.AbstractImpl.__delete__(collection, entityManager);
			else
				listener.delete(collection,entityManager);			
		}
		
		protected <T> void validatePreConditions(Class<T> tupleClass,Arguments<T> arguments) {
			if(tupleClass == null)
				throw new RuntimeException("tuple class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
		}
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		public static Boolean IS_TRANSACTIONAL = Boolean.FALSE;
		
		private Collection<T> providedCollection;
		private Collection<T> creatables;
		private Collection<T> updatables;
		private Collection<T> deletables;
		
		private Collection<T> existingCollection;
		
		private Boolean isNotBelogingToProvidedCollectionDeletable;
		
		private EntityManager entityManager;
		private Boolean isTransactional = IS_TRANSACTIONAL;
		private Listener<T> listener;
		
		private Collection<T> __creatables__;
		private Collection<T> __updatables__;
		private Collection<T> __deletables__;
		
		private Collection<Object> __existingIdentifiers__;
		
		public void computeExistingIdentifiers() {
			__existingIdentifiers__ = FieldHelper.readSystemIdentifiers(existingCollection);
		}
		
		/**
		 * Collect from provided where identifier is null OR not in existing
		 */
		public void computeCreatables() {
			__creatables__ = null;
			if(CollectionHelper.isEmpty(creatables) && CollectionHelper.isEmpty(providedCollection))
				return;
			if(CollectionHelper.isNotEmpty(creatables)) {
				if(__creatables__ == null)
					__creatables__ = new ArrayList<>();
				__creatables__.addAll(creatables);
			}
			if(CollectionHelper.isEmpty(providedCollection))
				return;
			for(T object : providedCollection) {
				Object identifier = FieldHelper.readSystemIdentifier(object);
				if(identifier == null || !CollectionHelper.contains(__existingIdentifiers__, identifier)) {
					if(__creatables__ == null)
						__creatables__ = new ArrayList<>();
					__creatables__.add(object);
				}
			}
		}
		
		/**
		 * Collect from provided where identifier is not null AND in existing
		 */
		protected void computeUpdatables() {
			__updatables__ = null;
			if(CollectionHelper.isNotEmpty(updatables)) {
				if(__updatables__ == null)
					__updatables__ = new ArrayList<>();
				__updatables__.addAll(updatables);
			}
			if(CollectionHelper.isEmpty(providedCollection))
				return;
			for(T object : providedCollection) {
				Object identifier = FieldHelper.readSystemIdentifier(object);
				if(identifier != null || CollectionHelper.contains(__existingIdentifiers__, identifier)) {
					if(__updatables__ == null)
						__updatables__ = new ArrayList<>();
					__updatables__.add(object);
				}
			}
		}
		/**
		 * Collect from existing where not in update able
		 */
		protected void computeDeletables() {
			__deletables__ = null;
			if(CollectionHelper.isNotEmpty(deletables)) {
				if(__deletables__ == null)
					__deletables__ = new ArrayList<>();
				__deletables__.addAll(deletables);
			}
			if(CollectionHelper.isEmpty(existingCollection))
				return;
			Collection<Object> updatablesIdentifiers = FieldHelper.readSystemIdentifiers(__updatables__);
			for(T object : existingCollection) {
				Object identifier = FieldHelper.readSystemIdentifier(object);
				if(!CollectionHelper.contains(updatablesIdentifiers, identifier)) {
					if(__deletables__ == null)
						__deletables__ = new ArrayList<>();
					__deletables__.add(object);
				}
			}
		}		
	
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Dto extends AbstractObject implements Serializable {
			
		}
	}
	
	public static interface Listener<T> {
		void create(Collection<T> collection,EntityManager entityManager);
		void update(Collection<T> collection,EntityManager entityManager);
		void delete(Collection<T> collection,EntityManager entityManager);
		
		public static abstract class AbstractImpl<T> extends AbstractObject implements Listener<T>,Serializable {
			@Override
			public void create(Collection<T> collection,EntityManager entityManager) {
				__create__(collection, entityManager);
			}

			@Override
			public void update(Collection<T> collection,EntityManager entityManager) {
				__update__(collection, entityManager);
			}

			@Override
			public void delete(Collection<T> collection,EntityManager entityManager) {
				__delete__(collection, entityManager);
			}
			
			/**/
			
			public static void __create__(Collection<?> collection,EntityManager entityManager) {
				if(CollectionHelper.isEmpty(collection))
					return;
				EntityCreator.getInstance().createMany((Collection<Object>)collection, entityManager);
			}
			
			public static void __update__(Collection<?> collection,EntityManager entityManager) {
				if(CollectionHelper.isEmpty(collection))
					return;
				EntityUpdater.getInstance().updateMany((Collection<Object>)collection,entityManager);
			}
			
			public static void __delete__(Collection<?> collection,EntityManager entityManager) {
				if(CollectionHelper.isEmpty(collection))
					return;
				EntityDeletor.getInstance().deleteMany((Collection<Object>)collection,entityManager);
			}
		}
	}
	
	/**/
	
	static EntitySaver getInstance() {
		return Helper.getInstance(EntitySaver.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}