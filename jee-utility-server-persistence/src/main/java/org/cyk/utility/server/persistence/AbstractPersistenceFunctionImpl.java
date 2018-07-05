package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.utility.architecture.system.AbstractSystemFunctionImpl;
import org.cyk.utility.architecture.system.SystemAction;
import org.cyk.utility.architecture.system.SystemActor;
import org.cyk.utility.architecture.system.SystemActorServer;
import org.cyk.utility.architecture.system.SystemLayer;
import org.cyk.utility.architecture.system.SystemLayerPersistence;
import org.cyk.utility.field.FieldGetName;
import org.cyk.utility.field.FieldGetValue;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionImpl extends AbstractSystemFunctionImpl implements PersistenceFunction, Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	protected void __execute__(SystemAction action) {
		if(Boolean.TRUE.equals(__isQueryExecutable__(action))){
			__executeQuery__(action);
			Object entity = getProperties().getEntity();
			if(entity == null){
				//TODO log warning
				//__getLog__().getMessageBuilder(Boolean.TRUE).addParameter("entity is null on "+action);
			}else{
				String systemIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
				String businessIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
				
				injectLog(action,entity).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE)
					.addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
					.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
						.getParent()
						//.execute()
						;	
			}
		}else{
			//TODO log warning
		}
		
	}
	
	protected abstract Boolean __isQueryExecutable__(SystemAction action);
	protected abstract void __executeQuery__(SystemAction action);
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setEntityManager(entityManager);
	}
	
	@Override
	public PersistenceFunction setAction(SystemAction action) {
		return (PersistenceFunction) super.setAction(action);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunction setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}
	
	@Override
	protected SystemActor getSystemActor() {
		return __inject__(SystemActorServer.class);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerPersistence.class);
	}

}
