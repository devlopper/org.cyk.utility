package org.cyk.utility.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Initializer {

	<T> void initialize(Class<T> klass,Arguments<T> arguments);
	<T> void initialize(Class<T> klass,Collection<T> entities,Object actionIdentifier);
	<T> void initialize(Class<T> klass,Collection<T> entities);
	<T> void initialize(Class<T> klass,T entity,Object actionIdentifier);
	<T> void initialize(Class<T> klass,T entity);
	
	public static abstract class AbstractImpl extends AbstractObject implements Initializer,Serializable {
		
		@Override
		public <T> void initialize(Class<T> klass, Arguments<T> arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("initializer entity class", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("initializer arguments", arguments);
			__initialize__(klass, arguments);
		}
		
		protected <T> void __initialize__(Class<T> klass, Arguments<T> arguments) {
			__initialize__(klass,arguments.entities, arguments.actionIdentifier);
		}
		
		protected <T> void __initialize__(Class<T> klass,Collection<T> entities, Object actionIdentifier) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("initializer entities", entities);
			entities.forEach(entity -> {
				__initialize__(klass, entity, actionIdentifier);
			});
		}
		
		protected <T> void __initialize__(Class<T> klass,T entity, Object actionIdentifier) {
			if(entity == null)
				return;
			____initialize____(klass, entity, actionIdentifier);
		}
		
		protected <T> void ____initialize____(Class<T> klass,T entity, Object actionIdentifier) {
			
		}
		
		@Override
		public <T> void initialize(Class<T> klass, Collection<T> entities, Object actionIdentifier) {
			initialize(klass, new Arguments<T>().setEntities(entities).setActionIdentifier(actionIdentifier));
		}
		
		@Override
		public <T> void initialize(Class<T> klass, Collection<T> entities) {
			initialize(klass, entities);
		}
		
		@Override
		public <T> void initialize(Class<T> klass, T entity, Object actionIdentifier) {
			__initialize__(klass, entity, actionIdentifier);
		}
		
		@Override
		public <T> void initialize(Class<T> klass, T entity) {
			initialize(klass, entity, null);
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		private Object actionIdentifier;
		private Collection<T> entities;
		
		public Collection<T> getEntities(Boolean injectIfNull) {
			if(entities == null && Boolean.TRUE.equals(injectIfNull))
				entities = new ArrayList<>();
			return entities;
		}
		
		public Arguments<T> addEntities(Collection<T> entities) {
			if(CollectionHelper.isEmpty(entities))
				return this;
			getEntities(Boolean.TRUE).addAll(entities);
			return this;
		}
		
		public Arguments<T> addEntities(T...entities) {
			if(ArrayHelper.isEmpty(entities))
				return this;
			return addEntities(CollectionHelper.listOf(entities));
		}
	}
	
	/**/
	
	static Initializer getInstance() {
		return Helper.getInstance(Initializer.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}