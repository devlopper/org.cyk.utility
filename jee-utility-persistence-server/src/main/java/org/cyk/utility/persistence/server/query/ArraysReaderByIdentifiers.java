package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;

public interface ArraysReaderByIdentifiers<ENTITY,IDENTIFIER> extends Reader<ENTITY, IDENTIFIER, Object[]> {

	Collection<Object[]> read(Collection<ENTITY> entities,Map<String,Object> parameters);
	Collection<Object[]> read(Map<String,Object> parameters,ENTITY...entities);
	Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	Collection<Object[]> readByIdentifiers(Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	void set(ENTITY entity,Object[] array);
	void set(Collection<ENTITY> entities,Collection<Object[]> arrays);
	
	void readThenSet(Collection<ENTITY> entities,Map<String,Object> parameters);
	
	<T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass,Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	<T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass,Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	Collection<ENTITY> readByIdentifiersThenInstantiate(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	Collection<ENTITY> readByIdentifiersThenInstantiate(Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER> extends Reader.AbstractImpl<ENTITY, IDENTIFIER, Object[]> implements ArraysReaderByIdentifiers<ENTITY,IDENTIFIER>,Serializable {
		
		@Override
		public Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			if(CollectionHelper.isEmpty(identifiers))
				return null;
			return new ReaderByCollection.AbstractImpl<IDENTIFIER,Object[]>() {
				@Override
				public Collection<Object[]> __read__(Collection<IDENTIFIER> values) {
					return __readByIdentifiers__(values,parameters);
				}
			}.read(identifiers);
		}

		protected Collection<Object[]> __readByIdentifiers__(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			Query query = instantiateQuery();
			setQueryParameters(query, identifiers,parameters);
			return query.getResultList();
		}
		
		protected Collection<IDENTIFIER> processIdentifiers(Collection<IDENTIFIER> identifiers) {
			return identifiers;
		}
		
		protected void setQueryParameters(Query query,Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			query.setParameter("identifiers", processIdentifiers(identifiers));
		}
		
		@Override
		public Collection<Object[]> readByIdentifiers(Map<String,Object> parameters,IDENTIFIER... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiers(CollectionHelper.listOf(identifiers),parameters);
		}
		
		@Override
		public Collection<Object[]> read(Map<String,Object> parameters,ENTITY... entities) {
			if(ArrayHelper.isEmpty(entities))
				return null;
			return read(CollectionHelper.listOf(entities),parameters);
		}
		
		@Override
		public Collection<Object[]> read(Collection<ENTITY> entities,Map<String,Object> parameters) {
			if(CollectionHelper.isEmpty(entities))
				return null;
			return readByIdentifiers((Collection<IDENTIFIER>) FieldHelper.readSystemIdentifiers(entities),parameters);
		}
		
		@Override
		public void set(ENTITY entity,Object[] array) {
			if(entity == null || array == null || array.length == 0)
				return;
			__set__(entity, array);
		}
		
		protected void __set__(ENTITY entity,Object[] array) {
			LogHelper.logWarning("set "+getEntityClass()+" not yet implemented", getClass());
		}
		
		protected void __setObject__(Object object,Object[] array) {
			LogHelper.logWarning("set object not yet implemented", getClass());
		}
		
		@Override
		public void set(Collection<ENTITY> entities,Collection<Object[]> arrays) {
			if(CollectionHelper.isEmpty(entities) || CollectionHelper.isEmpty(arrays))
				return;
			for(ENTITY entity : entities) {
				for(Object[] array : arrays) {
					if(isEntityArray(entity, array)) {
						__set__(entity, array);
						if(Boolean.TRUE.equals(isEntityHasOnlyArray(entity)))
							break;
					}
				}
			}
		}
		
		protected Boolean isEntityArray(ENTITY entity,Object[] array) {
			return array[0].equals(FieldHelper.readSystemIdentifier(entity));
		}
		
		protected Boolean isEntityHasOnlyArray(ENTITY entity) {
			return Boolean.TRUE;
		}
		
		@Override
		public void readThenSet(Collection<ENTITY> entities, Map<String, Object> parameters) {
			Collection<Object[]> arrays = read(entities, parameters);
			set(entities, arrays);
		}
		
		@Override
		public <T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass, Collection<IDENTIFIER> identifiers,Map<String, Object> parameters) {
			if(klass == null || CollectionHelper.isEmpty(identifiers))
				return null;
			Collection<Object[]> arrays = readByIdentifiers(identifiers, parameters);
			if(CollectionHelper.isEmpty(arrays))
				return null;
			Collection<T> collection = null;
			Class<?> entityClass = getEntityClass();
			for(Object[] array : arrays) {
				if(array == null)
					continue;
				T object = ClassHelper.instanciate(klass);
				if(klass.equals(entityClass))
					__set__((ENTITY) object, array);
				else
					__setObject__(object, array);
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(object);
			}
			return collection;
		}
		
		@Override
		public <T> Collection<T> readByIdentifiersThenInstantiate(Class<T> klass, Map<String, Object> parameters,IDENTIFIER... identifiers) {
			if(klass == null || ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiersThenInstantiate(klass,CollectionHelper.listOf(Boolean.TRUE, identifiers), parameters);
		}
		
		@Override
		public Collection<ENTITY> readByIdentifiersThenInstantiate(Collection<IDENTIFIER> identifiers,Map<String, Object> parameters) {
			return readByIdentifiersThenInstantiate(getEntityClass(), identifiers, parameters);
		}
		
		@Override
		public Collection<ENTITY> readByIdentifiersThenInstantiate(Map<String, Object> parameters,IDENTIFIER... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiersThenInstantiate(CollectionHelper.listOf(Boolean.TRUE, identifiers), parameters);
		}
		
		/**/
		
		protected static String getAsString(Object[] array,Integer index) {
			if(ArrayHelper.isEmpty(array) || NumberHelper.isLessThanZero(index))
				return null;
			return (String) array[index];
		}
		
		/**/
		
		public static abstract class DefaultImpl<ENTITY> extends ArraysReaderByIdentifiers.AbstractImpl<ENTITY, String> implements Serializable {
			
		}
	}
}