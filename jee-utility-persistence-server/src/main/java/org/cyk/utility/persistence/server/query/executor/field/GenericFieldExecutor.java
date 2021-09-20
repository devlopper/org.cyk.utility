package org.cyk.utility.persistence.server.query.executor.field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.PersistenceHelper;

public interface GenericFieldExecutor extends FieldBasedExecutor {

	/* Instance */
	
	<T,V> T getOne(Class<T> klass,Class<V> valueClass,String fieldName,V value);
	
	<T,V> Boolean exists(Class<T> klass,Class<V> valueClass,String fieldName,Collection<V> values);
	<T,V> Boolean exists(Class<T> klass,Class<V> valueClass,String fieldName,V...values);
	<T,V> void throwExceptionIfNotExist(Class<T> klass,Class<V> valueClass,String fieldName,V value);
	
	<T,V> Collection<V> getExisting(Class<T> klass,Class<V> valueClass,String fieldName,Collection<V> values);
	<T,V> Collection<V> getExisting(Class<T> klass,Class<V> valueClass,String fieldName,V...values);
	
	<T,V> Collection<V> getUnexisting(Class<T> klass,Class<V> valueClass,String fieldName,Collection<V> values);
	<T,V> Collection<V> getUnexisting(Class<T> klass,Class<V> valueClass,String fieldName,V...values);
	
	/* Value */
	
	<T,V> Collection<V> getValues(Class<T> klass,Class<V> valueClass,String fieldName);
	
	public static abstract class AbstractImpl extends FieldBasedExecutor.AbstractImpl implements GenericFieldExecutor,Serializable {
		
		@Override
		public <T, V> T getOne(Class<T> klass, Class<V> valueClass, String fieldName, V value) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || value == null)
				return null;
			T instance = null;
			try {
				instance = (T) EntityManagerGetter.getInstance().get().createQuery(String.format("SELECT t FROM %s t WHERE t.%s = :value"
						,PersistenceHelper.getEntityName(klass),fieldName),klass).setParameter("value", value).getSingleResult();
			} catch (NoResultException exception) {
				instance = null;
			}
			return instance;
		}
		
		@Override
		public <T,V> Boolean exists(Class<T> klass,Class<V> valueClass,String fieldName, Collection<V> values) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || CollectionHelper.isEmpty(values))
				return null;
			Long count = EntityManagerGetter.getInstance().get().createQuery(String.format("SELECT COUNT(t.identifier) FROM %s t WHERE t.%s IN :values"
					,PersistenceHelper.getEntityName(klass),fieldName),Long.class).setParameter("values", values).getSingleResult();
			return NumberHelper.compare(count, values.size(), ComparisonOperator.EQ);
		}
		
		@Override
		public <T,V> Boolean exists(Class<T> klass,Class<V> valueClass,String fieldName, V... values) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || ArrayHelper.isEmpty(values))
				return null;
			return exists(klass,valueClass,fieldName, CollectionHelper.listOf(values));
		}
		
		@Override
		public <T,V> void throwExceptionIfNotExist(Class<T> klass,Class<V> valueClass,String fieldName,V value) {
			if(ValueHelper.isBlank(value))
				throw new RuntimeException(String.format(getValueRequiredMessageFormat(klass, valueClass, fieldName, value),value));
			if(exists(klass,valueClass,fieldName, value))
				return;
			throw new RuntimeException(String.format(getValueNotExistMessageFormat(klass, valueClass, fieldName, value),value));
		}
		
		protected <T,V> String getValueRequiredMessageFormat(Class<T> klass,Class<V> valueClass,String fieldName,V value) {
			return getValueName(klass, valueClass, fieldName, value)+" <<%s>> obligatoire";
		}
		
		protected <T,V> String getValueNotExistMessageFormat(Class<T> klass,Class<V> valueClass,String fieldName,V value) {
			return getValueName(klass, valueClass, fieldName, value)+" <<%s>> inconnu";
		}
		
		protected <T,V> String getValueName(Class<T> klass,Class<V> valueClass,String fieldName,V value) {
			return fieldName;
		}
		
		@Override
		public <T, V> Collection<V> getExisting(Class<T> klass, Class<V> valueClass, String fieldName,Collection<V> values) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || CollectionHelper.isEmpty(values))
				return null;
			Collection<V> existing = null;
			for(V value : values) {
				if(value == null)
					continue;
				if(!Boolean.TRUE.equals(exists(klass, valueClass, fieldName, List.of(value))))
					continue;
				if(existing == null)
					existing = new ArrayList<>();
				existing.add(value);
			}
			return existing;
		}
		
		@Override
		public <T, V> Collection<V> getExisting(Class<T> klass, Class<V> valueClass, String fieldName, V... values) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || ArrayHelper.isEmpty(values))
				return null;
			return getExisting(klass,valueClass,fieldName, CollectionHelper.listOf(values));
		}
		
		@Override
		public <T, V> Collection<V> getUnexisting(Class<T> klass, Class<V> valueClass, String fieldName,Collection<V> values) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || CollectionHelper.isEmpty(values))
				return null;
			Collection<V> existing = getExisting(klass, valueClass, fieldName, values);
			if(CollectionHelper.isEmpty(existing))
				return values;
			Collection<V> unexisting = values.stream().filter(value -> !existing.contains(value)).collect(Collectors.toList());
			if(CollectionHelper.isEmpty(unexisting))
				return null;
			return unexisting;
		}
		
		@Override
		public <T, V> Collection<V> getUnexisting(Class<T> klass, Class<V> valueClass, String fieldName, V... values) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName) || ArrayHelper.isEmpty(values))
				return null;
			return getUnexisting(klass,valueClass,fieldName, CollectionHelper.listOf(values));
		}
	
		@Override
		public <T, V> Collection<V> getValues(Class<T> klass, Class<V> valueClass, String fieldName) {
			if(klass == null || valueClass == null || StringHelper.isBlank(fieldName))
				return null;
			Collection<V> values = EntityManagerGetter.getInstance().get().createQuery(String.format("SELECT DISTINCT t.%2$s FROM %1$s t ORDER BY t.%2$s",
					PersistenceHelper.getEntityName(klass),fieldName))
					.getResultList();
			if(CollectionHelper.isEmpty(values))
				return null;
			return values;
		}
	}
	
	/**/
	
	static GenericFieldExecutor getInstance() {
		return Helper.getInstance(GenericFieldExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}