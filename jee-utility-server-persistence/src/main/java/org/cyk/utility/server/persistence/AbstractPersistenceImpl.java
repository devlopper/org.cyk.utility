package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCount;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.value.ValueHelper;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends AbstractPersistenceServiceProviderImpl<Object> implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersistenceServiceProvider<Object> create(Object object, Properties properties) {
		PersistenceEntity<Object> persistence = injectInterfaceClassFromEntityClass(object);
		if(persistence == null){
			super.create(object, properties);
		}else{
			persistence.create(object, properties);
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY readOne(Class<ENTITY> aClass, Object identifier,Properties properties) {
		if(aClass == null)
			__injectThrowableHelper__().throwRuntimeException("class is required");
		if(identifier == null)
			__injectThrowableHelper__().throwRuntimeException("identifier is required");
		ValueUsageType valueUsageType = properties == null ? ValueUsageType.SYSTEM : (ValueUsageType) __inject__(ValueHelper.class).defaultToIfNull(properties.getValueUsageType(),ValueUsageType.SYSTEM);
		PersistenceEntity<ENTITY> persistence = injectInterfaceClassFromEntityClass(aClass);
		ENTITY entity;
		if(persistence == null){
			entity = (ENTITY) __inject__(PersistenceFunctionReader.class).setEntityClass(aClass).setEntityIdentifier(identifier)
					.setEntityIdentifierValueUsageType(valueUsageType).execute().getProperties().getEntity();
		}else{
			entity = /*ValueUsageType.SYSTEM.equals(valueUsageType) ?*/ persistence.readOne(identifier,valueUsageType) /*: persistence.readOneByBusinessIdentifier(identifier)*/;
		}
		return entity;
	}
	
	@Override
	public <ENTITY> ENTITY readOne(Class<ENTITY> aClass, Object identifier) {
		return readOne(aClass, identifier, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass, Properties properties) {
		PersistenceEntity<ENTITY> persistence = injectInterfaceClassFromEntityClass(aClass);
		Collection<ENTITY> entities;
		if(persistence == null){
			entities = (Collection<ENTITY>) __inject__(PersistenceFunctionReader.class).setEntityClass(aClass).execute().getProperties().getEntities();
		}else{
			entities =  persistence.readMany(properties);
		}
		return entities;
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass) {
		return readMany(aClass,null);
	}
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		PersistenceEntity<ENTITY> persistence = injectInterfaceClassFromEntityClass(aClass);
		Long count = null;
		if(persistence == null){
			__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
		}else{
			count =  persistence.count(properties);
		}
		return count;
	}
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		return count(aClass,null);
	}
	
	@Override
	public PersistenceServiceProvider<Object> update(Object object, Properties properties) {
		PersistenceEntity<Object> persistence = injectInterfaceClassFromEntityClass(object);
		if(persistence == null){
			super.update(object, properties);
		}else{
			persistence.update(object, properties);
		}
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<Object> delete(Object object, Properties properties) {
		PersistenceEntity<Object> persistence = injectInterfaceClassFromEntityClass(object);
		if(persistence == null){
			super.delete(object, properties);
		}else{
			persistence.delete(object, properties);
		}
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteAll(Class<ENTITY> aClass, Properties properties) {
		PersistenceEntity<ENTITY> persistence = injectInterfaceClassFromEntityClass(aClass);
		if(persistence == null){
			__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
		}else{
			persistence.deleteAll();
		}
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteAll(Class<ENTITY> aClass) {
		return deleteAll(aClass, null);
	}
	
	/**/
	
	/*protected <ENTITY> Class<PersistenceEntity<ENTITY>> __getPersistenceEntityClass__(Class<ENTITY> aClass,SystemAction action) {
		@SuppressWarnings("unchecked")
		Class<PersistenceEntity<ENTITY>> persistenceClass = (Class<PersistenceEntity<ENTITY>>) __inject__(SystemLayerPersistence.class).getInterfaceClassFromEntityClassName(aClass);
		if(persistenceClass == null){
			__logWarn__("No persistence interface found for entity "+aClass);
		}else {
			//__logInfo__("Using <"+persistenceClass+"> to "+action.getIdentifier()+" "+aClass);
		}
		return persistenceClass;
	}*/
}
