package org.cyk.utility.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Validator {

	<T> ThrowablesMessages validate(Class<T> klass,Arguments<T> arguments);
	<T> ThrowablesMessages validate(Class<T> klass,Collection<T> entities,Object actionIdentifier);
	
	public static abstract class AbstractImpl extends AbstractObject implements Validator,Serializable {
		
		@Override
		public <T> ThrowablesMessages validate(Class<T> klass, Arguments<T> arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("validator entity class", klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("validator arguments", arguments);
			return __validate__(klass, arguments);
		}
		
		protected <T> ThrowablesMessages __validate__(Class<T> klass, Arguments<T> arguments) {
			return __validate__(klass, arguments.entities, arguments.actionIdentifier);
		}
		
		protected <T> ThrowablesMessages __validate__(Class<T> klass,Collection<T> entities, Object actionIdentifier) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("validator entities", entities);
			ThrowablesMessages throwablesMessages = new ThrowablesMessages();
			entities.forEach(entity -> {
				__validate__(klass, entity, actionIdentifier, throwablesMessages);
			});
			if(CollectionHelper.isEmpty(throwablesMessages.getMessages()))
				return null;
			return throwablesMessages;
		}
		
		protected <T> void __validate__(Class<T> klass,T entity, Object actionIdentifier,ThrowablesMessages throwablesMessages) {
			
		}
		
		@Override
		public <T> ThrowablesMessages validate(Class<T> klass, Collection<T> entities, Object actionIdentifier) {
			return validate(klass, new Arguments<T>().setEntities(entities).setActionIdentifier(actionIdentifier));
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
	
	static Validator getInstance() {
		return Helper.getInstance(Validator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}