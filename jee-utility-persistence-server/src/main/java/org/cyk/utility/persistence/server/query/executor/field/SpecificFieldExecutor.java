package org.cyk.utility.persistence.server.query.executor.field;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

import lombok.Getter;

public interface SpecificFieldExecutor<V> extends FieldBasedExecutor {

	String getName();
	Class<V> getKlass();
	
	<T> Boolean exists(Class<T> klass,Collection<V> values);
	<T> Boolean exists(Class<T> klass,V...values);
	
	<T> Collection<V> getUnexisting(Class<T> klass,Collection<V> values);
	<T> Collection<V> getUnexisting(Class<T> klass,V...values);
	
	<T> Collection<V> getExisting(Class<T> klass,Collection<V> values);
	<T> Collection<V> getExisting(Class<T> klass,V...values);
	
	<T> Collection<V> getExistingFromIdentifiables(Class<T> klass,Collection<T> identifiables);
	<T> Collection<V> getExistingFromIdentifiables(Class<T> klass,T...identifiables);
	
	public static abstract class AbstractImpl<V> extends FieldBasedExecutor.AbstractImpl implements SpecificFieldExecutor<V>,Serializable {
		
		@Getter protected String name;
		@Getter protected Class<V> klass;
		
		public AbstractImpl(String name,Class<V> klass) {
			this.name = name;
			this.klass = klass;
		}
		
		@Override
		public <T> Boolean exists(Class<T> klass, Collection<V> values) {
			if(klass == null || CollectionHelper.isEmpty(values))
				return null;
			return GenericFieldExecutor.getInstance().exists(klass,getKlass(), getName(), values);
		}
		
		@Override
		public <T> Boolean exists(Class<T> klass, V... values) {
			if(klass == null || ArrayHelper.isEmpty(values))
				return null;
			return exists(klass, CollectionHelper.listOf(values));
		}
		
		@Override
		public <T> Collection<V> getUnexisting(Class<T> klass, Collection<V> values) {
			if(klass == null || CollectionHelper.isEmpty(values))
				return null;
			return GenericFieldExecutor.getInstance().getUnexisting(klass,getKlass(), getName(), values);
		}
		
		@Override
		public <T> Collection<V> getUnexisting(Class<T> klass, V... values) {
			if(klass == null || ArrayHelper.isEmpty(values))
				return null;
			return getUnexisting(klass, CollectionHelper.listOf(values));
		}
		
		@Override
		public <T> Collection<V> getExisting(Class<T> klass, Collection<V> values) {
			if(klass == null || CollectionHelper.isEmpty(values))
				return null;
			return GenericFieldExecutor.getInstance().getExisting(klass,getKlass(), getName(), values);
		}
		
		@Override
		public <T> Collection<V> getExisting(Class<T> klass, V... values) {
			if(klass == null || ArrayHelper.isEmpty(values))
				return null;
			return getExisting(klass, CollectionHelper.listOf(values));
		}
		
		@Override
		public <T> Collection<V> getExistingFromIdentifiables(Class<T> klass, Collection<T> identifiables) {
			if(klass == null || CollectionHelper.isEmpty(identifiables))
				return null;
			Collection<V> values = (Collection<V>) FieldHelper.readBusinessIdentifiersAsStrings(identifiables);
			return getExisting(klass, values);
		}
		
		@Override
		public <T> Collection<V> getExistingFromIdentifiables(Class<T> klass, T... identifiables) {
			if(klass == null || ArrayHelper.isEmpty(identifiables))
				return null;
			return getExistingFromIdentifiables(klass, CollectionHelper.listOf(identifiables));
		}
	}	
}