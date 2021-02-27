package org.cyk.utility.persistence.query;

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
import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.EntityManagerGetter;

public interface EntityCreator {

	void createMany(QueryExecutorArguments arguments);
	
	void createMany(Collection<Object> objects,EntityManager entityManager);
	
	void createMany(Collection<Object> objects);
	
	void createMany(EntityManager entityManager,Object...objects);
	
	void createMany(Object...objects);
	
	void createManyInTransaction(Collection<Object> objects);
	
	void createManyInTransaction(Object...objects);
	
	void createOne(Object object,EntityManager entityManager);
	
	void createOne(Object object);
	
	void createOne(Object object,Boolean isTransactional);
	
	void createOneInTransaction(Object object);
	
	void createByNativesQueriesStrings(Collection<String> queriesStrings);
	
	void createByNativesQueriesStrings(String...queriesStrings);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityCreator,Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override
		public void createMany(QueryExecutorArguments arguments) {
			if(arguments == null || CollectionHelper.isEmpty(arguments.getObjects()))
				return;
			EntityManager entityManager = arguments.getEntityManager();
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			if(Boolean.TRUE.equals(arguments.getIsTransactional()))
				entityManager.getTransaction().begin();
			for(Object index : arguments.getObjects()) {	
				__setSystemIdentifierIfNull__(index);
				__setNameIfNull__(index);
			}
			if(Boolean.TRUE.equals(arguments.getIsNative())) {
				Class<Object> klass = (Class<Object>) arguments.get__resultClass__();
				if(klass == null)
					klass = (Class<Object>) CollectionHelper.getFirst(arguments.getObjects()).getClass();
				String queryString = __inject__(NativeQueryStringBuilder.class).buildInsertMany(klass, (Collection<Object>)arguments.getObjects());
				entityManager.createNativeQuery(queryString).executeUpdate();
			}else {
				for(Object index : arguments.getObjects())
					entityManager.persist(index);
			}	
			if(Boolean.TRUE.equals(arguments.getIsTransactional()))
				entityManager.getTransaction().commit();
		}
		
		@Override
		public void createMany(Collection<Object> objects,EntityManager entityManager) {
			if(entityManager == null || CollectionHelper.isEmpty(objects))
				return;
			createMany(new QueryExecutorArguments().setObjects(objects).setEntityManager(entityManager));
		}
		
		@Override
		public void createMany(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			createMany(objects, EntityManagerGetter.getInstance().get());
		}
		
		@Override
		public void createMany(EntityManager entityManager,Object...objects) {
			if(entityManager == null || ArrayHelper.isEmpty(objects))
				return;
			createMany(CollectionHelper.listOf(objects),entityManager);
		}
		
		@Override
		public void createMany(Object...objects) {
			if(ArrayHelper.isEmpty(objects))
				return;
			createMany(CollectionHelper.listOf(objects));
		}
		
		@Override
		public void createManyInTransaction(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			createMany(new QueryExecutorArguments().addObjects(objects).setIsTransactional(Boolean.TRUE));
		}
		
		@Override
		public void createManyInTransaction(Object...objects) {
			if(ArrayHelper.isEmpty(objects))
				return;
			createMany(new QueryExecutorArguments().addObjects(CollectionHelper.listOf(objects)).setIsTransactional(Boolean.TRUE));
		}
		
		@Override
		public void createOne(Object object,EntityManager entityManager) {
			if(object == null || entityManager == null)
				return;
			createMany(CollectionHelper.listOf(object),entityManager);
		}
		
		@Override
		public void createOne(Object object) {
			if(object == null)
				return;
			createMany(new QueryExecutorArguments().addObjects(object));
		}
		
		@Override
		public void createOne(Object object,Boolean isTransactional) {
			if(object == null)
				return;
			createMany(new QueryExecutorArguments().addObjects(object).setIsTransactional(isTransactional));
		}
		
		@Override
		public void createOneInTransaction(Object object) {
			if(object == null)
				return;
			createOne(object,Boolean.TRUE);
		}

		@Override
		public void createByNativesQueriesStrings(Collection<String> queriesStrings) {
			if(CollectionHelper.isEmpty(queriesStrings))
				return;
			EntityManager entityManager = EntityManagerGetter.getInstance().get();
			queriesStrings.stream().filter(queryString -> StringHelper.isNotBlank(queryString)).forEach(queryString -> {
				entityManager.createNativeQuery(queryString).executeUpdate();
			});
		}
		
		@Override
		public void createByNativesQueriesStrings(String... queriesStrings) {
			if(ArrayHelper.isEmpty(queriesStrings))
				return;
			createByNativesQueriesStrings(CollectionHelper.listOf(queriesStrings));
		}
		
		/**/
		
		protected void __setSystemIdentifierIfNull__(Object object) {
			if(!(object instanceof IdentifiableSystem))
				return;
			Object identifier = FieldHelper.readSystemIdentifier(object);
			if(identifier != null)
				return;			
			//if business identifier is not null then we assign business identifier to system identifier else we generate one			
			identifier = FieldHelper.readBusinessIdentifier(object);
			if(identifier == null)
				identifier = UUID.randomUUID().toString();			
			((IdentifiableSystem<Object>)object).setSystemIdentifier(identifier);				
		}
		
		protected void __setNameIfNull__(Object object) {
			if(!(object instanceof Namable)) 
				return;			
			Object name = ((Namable)object).getName();
			if(name != null)
				return;			
			//if business identifier is not null then we assign business identifier to name			
			name = FieldHelper.readBusinessIdentifier(object);
			if(name == null)
				return;
			((Namable)object).setName(name.toString());										
		}
	}
	
	/**/
	
	static EntityCreator getInstance() {
		return Helper.getInstance(EntityCreator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}