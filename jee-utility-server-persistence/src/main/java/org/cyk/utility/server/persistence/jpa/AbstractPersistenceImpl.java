package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.utility.field.FieldGetName;
import org.cyk.utility.field.FieldGetValue;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
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
	public Persistence create(Object entity) {
		if(entity == null){
			__inject__(Log.class).executeTrace("entity must not be null");
		}else{
			getEntityManager().persist(entity);
			String systemIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
			String businessIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
			__inject__(Log.class).setLevel(LogLevel.INFO).addMarkers("PERSISTENCE").getMessageBuilder(Boolean.TRUE)
				.addParameter("entity created").addParameter("class", entity.getClass().getName()).addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class)
						.setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
				.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
					.getParent().execute();
		}
		return this;
	}

	@Override
	public <ENTITY> ENTITY read(Class<ENTITY> aClass, Object identifier) {
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
				__inject__(Log.class).setLevel(LogLevel.INFO).addMarkers("PERSISTENCE").getMessageBuilder(Boolean.TRUE)
					.addParameter("entity read").addParameter("class", entity.getClass().getName()).addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class)
							.setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
					.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
						.getParent().execute();	
			}
			
		}
		return entity;
	}

	@Override
	public <ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persistence update(Object entity) {
		getEntityManager().merge(entity);
		return this;
	}

	@Override
	public Persistence delete(Object entity) {
		getEntityManager().remove(entity);
		return this;
	}

	@Override
	public <ENTITY> Long countAll(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}
}
