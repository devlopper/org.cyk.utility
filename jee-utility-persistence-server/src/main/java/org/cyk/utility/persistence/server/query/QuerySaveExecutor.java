package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.server.EntityManagerGetter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QuerySaveExecutor {

	<T> void executeSave(Class<T> resultClass,Arguments<T> arguments);
	
	/**/
	
	public abstract class AbstractQuerySaveExecutorImpl extends AbstractObject implements QuerySaveExecutor,Serializable {

		@Override
		public <T> void executeSave(Class<T> resultClass,Arguments<T> arguments) {
			validatePreConditions(resultClass,arguments);
			//deduce the partitions : 1 : creation - 2 : modification - 3 : deletion
			Collection<T> creation = null;
			Collection<Object> providedSystemIdentifiers = null;
			if(CollectionHelper.isNotEmpty(arguments.getProvidedCollection()))
				for(T entity : arguments.getProvidedCollection()) {
					Object systemIdentifier = FieldHelper.readSystemIdentifier(entity);
					if(systemIdentifier == null) {
						if(creation == null)
							creation = new ArrayList<>();
						creation.add(entity);
					}else {
						if(providedSystemIdentifiers == null)
							providedSystemIdentifiers = new LinkedHashSet<>();
						providedSystemIdentifiers.add(systemIdentifier);
					}
				}
			
			Collection<T> deletion = null;
			Collection<T> modification = null;
			if(CollectionHelper.isNotEmpty(arguments.getExistingCollection()))
				for(T entity : arguments.getExistingCollection()) {
					if(CollectionHelper.contains(providedSystemIdentifiers, FieldHelper.readSystemIdentifier(entity))) {
						if(modification == null)
							modification = new ArrayList<>();
						modification.add(entity);
					}else {
						if(deletion == null)
							deletion = new ArrayList<>();
						deletion.add(entity);
					}
				}
			
			if(arguments.listener == null) {
				if(arguments.entityManager == null)
					throw new RuntimeException("Entity manager is required to create,update or delete");
				throw new RuntimeException("Create,update and delete not yet handled");
			}else {
				//arguments.listener.create(creation);
				//arguments.listener.update(modification);
				//arguments.listener.delete(deletion);
			}
		}
		
		/**/
		
		protected <T> void validatePreConditions(Class<T> resultClass,Arguments<?> arguments) {
			if(resultClass == null)
				throw new RuntimeException("result class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(CollectionHelper.isEmpty(arguments.getProvidedCollection()))
				throw new RuntimeException("query provided collection is required");
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		private Collection<T> providedCollection;
		private Collection<T> existingCollection;
		private EntityManager entityManager;
		private Listener listener;
	}
	
	public static interface Listener {
		void create(Collection<?> collection,EntityManager entityManager);
		void update(Collection<?> collection,EntityManager entityManager);
		void delete(Collection<?> collection,EntityManager entityManager);
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			@Override
			public void create(Collection<?> collection,EntityManager entityManager) {
				__create__(collection, entityManager);
			}

			@Override
			public void update(Collection<?> collection,EntityManager entityManager) {
				__update__(collection, entityManager);
			}

			@Override
			public void delete(Collection<?> collection,EntityManager entityManager) {
				__delete__(collection, entityManager);
			}
			
			/**/
			
			public static void __create__(Collection<?> collection,EntityManager entityManager) {
				if(CollectionHelper.isEmpty(collection))
					return;
				if(entityManager == null)
					entityManager = EntityManagerGetter.getInstance().get();
				for(Object object : collection)
					entityManager.persist(object);
			}
			
			public static void __update__(Collection<?> collection,EntityManager entityManager) {
				if(CollectionHelper.isEmpty(collection))
					return;
				if(entityManager == null)
					entityManager = EntityManagerGetter.getInstance().get();
				for(Object object : collection)
					entityManager.merge(object);
			}
			
			public static void __delete__(Collection<?> collection,EntityManager entityManager) {
				if(CollectionHelper.isEmpty(collection))
					return;
				if(entityManager == null)
					entityManager = EntityManagerGetter.getInstance().get();
				for(Object object : collection)
					entityManager.remove(object);
			}
		}
	}
	
	/**/
	
	static QuerySaveExecutor getInstance() {
		return Helper.getInstance(QuerySaveExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}