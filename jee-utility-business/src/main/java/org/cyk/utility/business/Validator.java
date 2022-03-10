package org.cyk.utility.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Validator {

	<T> ThrowablesMessages validate(Class<T> klass,Arguments<T> arguments);
	<T> ThrowablesMessages validate(Class<T> klass,Collection<T> entities,Object actionIdentifier);

	void validateAuditWho(String auditWho,ThrowablesMessages throwablesMessages);
	
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
		
		@Override
		public void validateAuditWho(String auditWho, ThrowablesMessages throwablesMessages) {
			throwablesMessages.addIfTrue(getValidateAuditWhoMessage(), StringHelper.isBlank(auditWho));
		}
		
		protected String getValidateAuditWhoMessage() {
			return "Le nom d'utilisateur est requis";
		}
		
		public static void validateIdentifiers(Collection<String> providedIdentifiers,Collection<String> systemIdentifiers,ThrowablesMessages throwablesMessages) {
			if(CollectionHelper.isEmpty(providedIdentifiers))
				return;
			providedIdentifiers.forEach(identifier -> {
				throwablesMessages.addIfTrue(String.format("L'identifiant %s n'existe pas", identifier), !Boolean.TRUE.equals(CollectionHelper.contains(systemIdentifiers, identifier)));
			});
		}
	
		public static void validateIdentifier(String identifier,String name,ThrowablesMessages throwablesMessages) {
			if(StringHelper.isNotBlank(identifier))
				return;
			throwablesMessages.add(String.format("L'identifiant de %s est requis",name));
		}
		
		public static <T> T validateExistenceAndReturn(Class<T> klass,String identifier,Collection<String> projections,SpecificPersistence<T> persistence,String name,ThrowablesMessages throwablesMessages,EntityManager entityManager) {
			T instance = null;
			if(StringHelper.isNotBlank(identifier)) {
				if(persistence == null)
					instance = entityManager == null ? null : entityManager.find(klass, identifier);
				else
					instance = persistence.readOne(new QueryExecutorArguments().addProjectionsFromStrings(projections).addFilterField(persistence.getParameterNameIdentifier(), identifier).setEntityManager(entityManager));
			}
			if(instance == null)
				throwablesMessages.add(String.format("%s identifiée par %s n'existe pas",name, identifier));
			return instance;
		}
		
		public static <T> T validateExistenceAndReturn(Class<T> klass,String identifier,Collection<String> projections,SpecificPersistence<T> persistence,String name,ThrowablesMessages throwablesMessages) {
			return validateExistenceAndReturn(klass, identifier, projections, persistence, name, throwablesMessages, null);
		}
		
		public static <T> T validateExistenceAndReturn(Class<T> klass,String identifier,Collection<String> projections,SpecificPersistence<T> persistence,ThrowablesMessages throwablesMessages,EntityManager entityManager) {
			String name = (String) FieldHelper.readStatic(klass, "NAME");
			return validateExistenceAndReturn(klass, identifier,projections, persistence,name, throwablesMessages,entityManager);
		}
		
		public static <T> T validateExistenceAndReturn(Class<T> klass,String identifier,Collection<String> projections,SpecificPersistence<T> persistence,ThrowablesMessages throwablesMessages) {
			return validateExistenceAndReturn(klass, identifier, projections, persistence, throwablesMessages, null);
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