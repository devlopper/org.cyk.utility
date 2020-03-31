package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.PersistenceUnitHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.jboss.weld.exceptions.IllegalArgumentException;

public interface QueryResultProcessor {

	<T> void process(Class<T> klass,Collection<T> entities,QueryExecutorArguments arguments);
	
	default <T> void process(Class<T> klass,Collection<T> entities) {
		if(klass == null)
			throw new IllegalArgumentException("class is required");
		process(klass, entities,null);
	}
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements QueryResultProcessor,Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public <T> void process(Class<T> klass, Collection<T> entities, QueryExecutorArguments arguments) {
			if(klass == null)
				throw new IllegalArgumentException("class is required");
			if(CollectionHelper.isEmpty(entities))
				return;
			__process__(klass, entities, arguments);
		}
		
		protected <T> void __process__(Class<T> klass, Collection<T> entities, QueryExecutorArguments arguments) {
			__initializeLazyAttributes__(klass, entities, arguments == null || arguments.getProperties() == null || arguments.getProperties().getEntityGraph() == null ? null 
					: arguments.getProperties().getEntityGraph().getAttributes());
		}
		
		protected <T> void __initializeLazyAttributes__(Class<T> klass, Collection<T> entities, EntityGraphArguments.Attributes attributes) {
			Collection<Field> lazyFields = PersistenceUnitHelper.getLazyAssociationsFields(klass);
			if(CollectionHelper.isEmpty(lazyFields))
				return;
			Collection<Field> lazyFieldsNotYetInitialized = lazyFields.stream().filter(field -> attributes==null || !attributes.isContainsName(field.getName())).collect(Collectors.toList());
			if(CollectionHelper.isEmpty(lazyFieldsNotYetInitialized))
				return;
			entities.parallelStream().forEach(entity -> {
				__initializeLazyAttributes__(entity, lazyFieldsNotYetInitialized,attributes);
			});
		}
		
		protected <T> void __initializeLazyAttributes__(Object entity,Collection<Field> lazyFields, EntityGraphArguments.Attributes attributes) {
			lazyFields.forEach(field-> {FieldHelper.write(entity, field, null);});
		}
	}
	
	/**/
	
	static QueryResultProcessor getInstance() {
		return Helper.getInstance(QueryResultProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}