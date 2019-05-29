package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;

public abstract class AbstractBusinessEntityImpl<ENTITY,PERSISTENCE extends PersistenceEntity<ENTITY>> extends AbstractBusinessServiceProviderImpl<ENTITY> implements BusinessEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Class<ENTITY> persistenceEntityClass;
	@Getter private PERSISTENCE persistence;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		persistenceEntityClass = __getPersistenceEntityClass__();
		if(persistenceEntityClass == null) {
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+getClass()+" : persistence entity class cannot be derived <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}else {
			persistence = (PERSISTENCE) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(getPersistenceEntityClass());	
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getPersistenceEntityClass__(){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ENTITY findOne(Object identifier, Properties properties) {
		ENTITY entity = (ENTITY) __inject__(BusinessFunctionReader.class).setEntityClass(getPersistenceEntityClass()).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(properties == null ? ValueUsageType.SYSTEM: (ValueUsageType) properties.getValueUsageType()).execute().getProperties().getEntity();
		__listenFindOneAfter__(entity, properties);
		return entity;
	}

	@Override
	public ENTITY findOne(Object identifier, ValueUsageType valueUsageType) {
		return findOne(identifier,new Properties().setValueUsageType(valueUsageType));
	}

	@Override
	public ENTITY findOne(Object identifier) {
		return findOne(identifier,(Properties)null);
	}

	@Override
	public ENTITY findOneByBusinessIdentifier(Object identifier) {
		return findOne(identifier, ValueUsageType.BUSINESS);
	}
	
	@Override
	public ENTITY findOneBySystemIdentifier(Object identifier) {
		return findOne(identifier, ValueUsageType.SYSTEM);
	}

	@Override
	public Collection<ENTITY> findMany(Properties properties) {
		Collection<ENTITY> entities = getPersistence().readMany(properties);
		__listenFindManyAfter__(entities,properties);
		return entities;
	}

	@Override
	public Collection<ENTITY> findMany() {
		//TODO use default settings like pagination and sorting
		return findMany(null);
	}

	protected void __listenFindManyAfter__(Collection<ENTITY> entities,Properties properties) {
		if(__injectCollectionHelper__().isNotEmpty(entities)) {
			for(ENTITY index : entities)
				__processAfterRead__(index,properties);	
		}
	}
	
	protected void __listenFindOneAfter__(ENTITY entity,Properties properties) {
		if(entity != null)
			__processAfterRead__(entity, properties);
	}
	
	protected void __processAfterRead__(ENTITY entity,Properties properties) {
		
	}
	
	@Override
	public Long count(Properties properties) {
		return getPersistence().count(properties);
	}

	@Override
	public Long count() {
		//TODO use default settings like pagination and sorting
		return count(null);
	}
	
	/**/
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType) {
		delete(getPersistence().readOne(identifier,valueUsageType));
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteBySystemIdentifier(Object identifier) {
		deleteByIdentifier(identifier,ValueUsageType.SYSTEM);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByBusinessIdentifier(Object identifier) {
		deleteByIdentifier(identifier,ValueUsageType.BUSINESS);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteAll() {
		BusinessFunctionRemover function = __inject__(BusinessFunctionRemover.class);
		function.setAll(Boolean.TRUE).setEntityClass(getPersistenceEntityClass());
		function.execute();
		return this;
	}
	
	/**/
	
	protected void __create__(Object object) {
		__inject__(Business.class).create(object);
	}
	
	protected void __createIfSystemIdentifierIsBlank__(Object object) {
		__inject__(Business.class).create(object,new Properties().setIsCreateIfSystemIdentifierIsBlank(Boolean.TRUE));	
	}
}
