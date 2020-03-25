package org.cyk.utility.__kernel__.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityCreator;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EntitySaver {

	<T> void save(Class<T> tupleClass,Arguments<T> arguments);
	
	/**/
	
	public abstract class AbstractEntitySaverImpl extends AbstractObject implements EntitySaver,Serializable {

		@Override
		public <T> void save(Class<T> tupleClass,Arguments<T> arguments) {
			validatePreConditions(tupleClass,arguments);
			if(CollectionHelper.isEmpty(arguments.providedCollection) && CollectionHelper.isEmpty(arguments.existingCollection))
				return;
			//deduce the partitions : 1 : creation - 2 : modification - 3 : deletion
			Collection<T> creation = null;
			Collection<Object> existingSystemIdentifiers = null;
			if(CollectionHelper.isNotEmpty(arguments.getExistingCollection()))
				existingSystemIdentifiers = arguments.getExistingCollection().stream().map(tuple -> FieldHelper.readSystemIdentifier(tuple)).collect(Collectors.toSet());
			Collection<Object> providedSystemIdentifiers = null;
			if(CollectionHelper.isNotEmpty(arguments.getProvidedCollection()))
				for(T entity : arguments.getProvidedCollection()) {
					Object systemIdentifier = FieldHelper.readSystemIdentifier(entity);
					if(!CollectionHelper.contains(existingSystemIdentifiers, systemIdentifier)) {
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
			
			EntityManager entityManager = arguments.getEntityManager();
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			if(Boolean.TRUE.equals(arguments.isTransactional))
				entityManager.getTransaction().begin();
			if(arguments.listener == null) {
				Listener.AbstractImpl.__create__(creation, entityManager);
				Listener.AbstractImpl.__update__(modification, entityManager);
				Listener.AbstractImpl.__delete__(deletion, entityManager);
			}else {
				arguments.listener.create(creation,entityManager);
				arguments.listener.update(modification,entityManager);
				arguments.listener.delete(deletion,entityManager);
			}
			if(Boolean.TRUE.equals(arguments.isTransactional))
				entityManager.getTransaction().commit();
		}
		
		/**/
		
		protected <T> void validatePreConditions(Class<T> tupleClass,Arguments<?> arguments) {
			if(tupleClass == null)
				throw new RuntimeException("tuple class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		private Collection<T> providedCollection;
		private Collection<T> existingCollection;
		private EntityManager entityManager;
		private Boolean isTransactional;
		private Listener<T> listener;
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
				EntityCreator.getInstance().createMany((Collection<Object>)collection, entityManager);
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
					entityManager.remove(entityManager.merge(object));
			}
		}
	}
	
	/**/
	
	static EntitySaver getInstance() {
		return Helper.getInstance(EntitySaver.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}