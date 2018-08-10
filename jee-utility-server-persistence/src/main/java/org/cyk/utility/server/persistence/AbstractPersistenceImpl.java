package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.value.ValueHelper;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends AbstractPersistenceServiceProviderImpl<Object> implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY readOne(Class<ENTITY> aClass, Object identifier,Properties properties) {
		ValueUsageType valueUsageType = properties == null ? ValueUsageType.SYSTEM : (ValueUsageType) __inject__(ValueHelper.class).defaultToIfNull(properties.getValueUsageType(),ValueUsageType.SYSTEM);
		Class<PersistenceEntity<ENTITY>> persistenceClass = (Class<PersistenceEntity<ENTITY>>) __inject__(SystemLayerPersistence.class).getInterfaceClassFromEntityClassName(aClass);
		ENTITY entity;
		if(persistenceClass == null){
			__logWarn__("No persistence interface found for entity "+aClass);
			entity = (ENTITY) __inject__(PersistenceFunctionReader.class).setEntityClass(aClass).setEntityIdentifier(identifier)
					.setEntityIdentifierValueUsageType(valueUsageType).execute().getProperties().getEntity();
		}else{
			entity = ValueUsageType.SYSTEM.equals(valueUsageType) ? __inject__(persistenceClass).readOne(identifier) : __inject__(persistenceClass).readOneByBusinessIdentifier(identifier);
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
		return (Collection<ENTITY>) __inject__(PersistenceFunctionReader.class).setEntityClass(aClass).execute().getProperties().getEntities();
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass) {
		return readMany(aClass,null);
	}
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		return count(aClass,null);
	}
	
	@Override
	public <ENTITY> Persistence deleteAll(Class<ENTITY> aClass, Properties properties) {
		@SuppressWarnings("unchecked")
		Class<PersistenceEntity<ENTITY>> persistenceClass = (Class<PersistenceEntity<ENTITY>>) __inject__(SystemLayerPersistence.class).getInterfaceClassFromEntityClassName(aClass);
		if(persistenceClass == null){
			__logWarn__("No persistence interface found for entity "+aClass);
			__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
		}else{
			__inject__(persistenceClass).deleteAll();
		}
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteAll(Class<ENTITY> aClass) {
		return deleteAll(aClass, null);
	}
	
	@Override
	public PersistenceServiceProvider<Object> deleteAll() {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
		return null;
	}
	
	/**/
	
}
