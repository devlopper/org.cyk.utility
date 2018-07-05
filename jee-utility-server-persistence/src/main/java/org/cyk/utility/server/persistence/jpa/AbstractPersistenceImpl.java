package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.architecture.system.SystemAction;
import org.cyk.utility.architecture.system.SystemActionCreate;
import org.cyk.utility.architecture.system.SystemActionRead;
import org.cyk.utility.field.FieldGetName;
import org.cyk.utility.field.FieldGetValue;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.server.persistence.PersistenceFunctionCreate;
import org.cyk.utility.server.persistence.PersistenceFunctionRead;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends org.cyk.utility.server.persistence.AbstractPersistenceImpl implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Persistence create(Object entity,Properties properties) {
		__inject__(PersistenceFunctionCreate.class).setAction(properties == null || properties.getAction() == null ? __inject__(SystemActionCreate.class) 
				: (SystemAction) properties.getAction()).setEntity(entity).execute();
		/*
		SystemAction action = properties == null || properties.getAction() == null ? __inject__(SystemActionCreate.class) : (SystemAction) properties.getAction();
		if(entity == null){
			__inject__(Log.class).executeTrace("entity must not be null");
		}else{
			getEntityManager().persist(entity);
			String systemIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
			String businessIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
			
			injectLog(action,entity).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE)
				.addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
				.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
					.getParent().execute();
		}
		*/
		return this;
	}
	
	@Override
	public Persistence create(Object entity) {
		return create(entity, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY read(Class<ENTITY> aClass, Object identifier,Properties properties) {
		/*SystemAction action = properties == null || properties.getAction() == null ? __inject__(SystemActionRead.class) : (SystemAction) properties.getAction();
		ENTITY entity = null;
		if(aClass == null || identifier == null){
			__inject__(Log.class).executeTrace("class and identifier must not be null");
		}else{
			entity = getEntityManager().find(aClass,identifier);
			if(entity == null){
				//TODO log not found
			}else{
				String systemIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
				String businessIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
				injectLog(action,entity).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE)
				.addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
				.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
					.getParent().execute();
			}
			
		}
		return entity;
		*/
		
		return (ENTITY) __inject__(PersistenceFunctionRead.class).setAction(properties == null || properties.getAction() == null ? __inject__(SystemActionRead.class) 
				: (SystemAction) properties.getAction()).setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
	}
	
	@Override
	public <ENTITY> ENTITY read(Class<ENTITY> aClass, Object identifier) {
		return read(aClass, identifier, null);
	}

	@Override
	public <ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persistence update(Object entity, Properties properties) {
		getEntityManager().merge(entity);
		return this;
	}
	
	@Override
	public Persistence update(Object entity) {
		update(entity, null);
		return this;
	}

	@Override
	public Persistence delete(Object entity, Properties properties) {
		getEntityManager().remove(getEntityManager().merge(entity));
		return this;
	}
	
	@Override
	public Persistence delete(Object entity) {
		return delete(entity, null);
	}

	@Override
	public <ENTITY> Long countAll(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <ENTITY> Long countAll(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}
}
