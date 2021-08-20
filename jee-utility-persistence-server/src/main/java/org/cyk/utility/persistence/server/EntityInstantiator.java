package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EntityInstantiator {

	<T> Collection<T> instantiateMany(Class<T> klass,Arguments arguments);
	<T> Collection<T> instantiateMany(Class<T> klass,Collection<Map<String,Object>> instancesFieldsNamesValues);
	<T> T instantiateOne(Class<T> klass,Arguments arguments);
	<T> T instantiateOne(Class<T> klass,Map<String,Object> fieldsNamesValues);
	<T> T instantiateOne(Class<T> klass,Filter filter);
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityInstantiator,Serializable {
		@Override
		public <T> Collection<T> instantiateMany(Class<T> klass, Arguments arguments) {		
			return __instantiate__(klass, arguments.instancesFieldsNamesValues);
		}
		
		protected <T> Collection<T> __instantiate__(Class<T> klass, Collection<Map<String,Object>> instancesFieldsNamesValues) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("klass", klass);
			if(CollectionHelper.isEmpty(instancesFieldsNamesValues))
				return null;
			Collection<T> instances = new ArrayList<>();
			instancesFieldsNamesValues.forEach(fieldsNamesValues -> {
				instances.add(__instantiate__(klass, fieldsNamesValues));
			});
			return instances;
		}
		
		protected <T> T __instantiate__(Class<T> klass, Map<String,Object> fieldsNamesValues) {
			T instance = ClassHelper.instanciate(klass);
			if(MapHelper.isEmpty(fieldsNamesValues))
				return instance;
			__instantiate__(instance, fieldsNamesValues);
			return instance;
		}
		
		protected void __instantiate__(Object instance, Map<String,Object> fieldsNamesValues) {
			throw new RuntimeException(String.format("Instantiate entity of type %s not yet implemented", instance.getClass()));
		}
		
		@Override
		public <T> Collection<T> instantiateMany(Class<T> klass,Collection<Map<String, Object>> instancesFieldsNamesValues) {
			return instantiateMany(klass, new Arguments().setInstancesFieldsNamesValues(instancesFieldsNamesValues));
		}
		
		@Override
		public <T> T instantiateOne(Class<T> klass, Arguments arguments) {
			Collection<T> instances = __instantiate__(klass, arguments.instancesFieldsNamesValues);
			if(CollectionHelper.isEmpty(instances))
				return null;
			if(instances.size() > 1)
				throw new RuntimeException(String.format("Too much (%s) results found", instances.size()));
			return CollectionHelper.getFirst(instances);
		}
		
		@Override
		public <T> T instantiateOne(Class<T> klass, Map<String, Object> fieldsNamesValues) {
			return instantiateOne(klass, new Arguments().setInstancesFieldsNamesValues(CollectionHelper.listOf(Boolean.TRUE,fieldsNamesValues)));
		}
		
		@Override
		public <T> T instantiateOne(Class<T> klass, Filter filter) {
			return instantiateOne(klass, filter == null ? null : filter.generateMapStringObject());
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Collection<Map<String,Object>> instancesFieldsNamesValues;
	}
	
	/**/
	
	static EntityInstantiator getInstance() {
		return Helper.getInstance(EntityInstantiator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}