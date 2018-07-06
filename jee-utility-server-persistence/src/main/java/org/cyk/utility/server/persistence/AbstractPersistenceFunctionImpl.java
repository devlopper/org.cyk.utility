package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.utility.architecture.system.AbstractSystemFunctionImpl;
import org.cyk.utility.architecture.system.SystemAction;
import org.cyk.utility.architecture.system.SystemActor;
import org.cyk.utility.architecture.system.SystemActorServer;
import org.cyk.utility.architecture.system.SystemLayer;
import org.cyk.utility.architecture.system.SystemLayerPersistence;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldGetName;
import org.cyk.utility.field.FieldGetValue;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionImpl extends AbstractSystemFunctionImpl implements PersistenceFunction, Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	protected void __execute__(SystemAction action) {
		__getLog__().setLevel(LogLevel.INFO);
		Class<?> entityClass = getEntityClass();
		addLogMarkers(action.getIdentifier().toString(),entityClass.getSimpleName());
		__getLog__().getMessageBuilder(Boolean.TRUE).addParameter(__inject__(StringHelper.class).concatenate(getLogMarkers(),CharacterConstant.SPACE.toString()));
		if(Boolean.TRUE.equals(__isQueryExecutable__(action))){
			__executeQuery__(action);
			Object entity = getProperties().getEntity();
			Collection<FieldName> fieldNames = getLoggedEntityFieldNames();
			if(__inject__(CollectionHelper.class).isNotEmpty(fieldNames)){
				for(FieldName index : fieldNames){
					Collection<ValueUsageType> valueUsageTypes = getValueUsageTypes(index);
					if(__inject__(CollectionHelper.class).isNotEmpty(valueUsageTypes))
						for(ValueUsageType indexValueUsageType : valueUsageTypes){
							String fieldName = __inject__(FieldGetName.class).execute(entityClass, index,indexValueUsageType).getOutput();
							__getLog__().getMessageBuilder().addParameter(fieldName, getEnityFieldValue(entity,index,indexValueUsageType, fieldName));	
						}
				}
			}
		}else{
			//TODO log warning
		}
		
	}
	
	protected abstract Boolean __isQueryExecutable__(SystemAction action);
	protected abstract void __executeQuery__(SystemAction action);
	
	protected Collection<FieldName> getLoggedEntityFieldNames(){
		return __inject__(CollectionHelper.class).instanciate(FieldName.IDENTIFIER);
	}
	
	protected Collection<ValueUsageType> getValueUsageTypes(FieldName fieldName){
		return __inject__(CollectionHelper.class).instanciate(ValueUsageType.values());
	}
	
	protected Object getEnityFieldValue(Object entity,FieldName fieldName,ValueUsageType valueUsageType,String derivedFieldName){
		return __inject__(FieldGetValue.class).setObject(entity).setField(derivedFieldName).execute().getOutput();
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setEntityManager(entityManager);
		addLogMarkers(__inject__(SystemLayerPersistence.class).getIdentifier().toString());
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
	public Class<?> getEntityClass() {
		return (Class<?>) getProperties().getEntityClass();
	}
	
	@Override
	public PersistenceFunction setEntityClass(Class<?> aClass) {
		getProperties().setEntityClass(aClass);
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
