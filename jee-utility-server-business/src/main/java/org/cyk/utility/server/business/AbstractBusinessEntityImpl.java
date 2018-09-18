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
		persistenceEntityClass = __getEntityClass__();
		if(persistenceEntityClass == null) {
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+getClass()+" : persistence entity class cannot be derived <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}else {
			persistence = (PERSISTENCE) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(getPersistenceEntityClass());	
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ENTITY findOne(Object identifier, Properties properties) {
		return (ENTITY) __inject__(BusinessFunctionReader.class).setEntityClass(getPersistenceEntityClass()).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(properties == null ? ValueUsageType.SYSTEM: (ValueUsageType) properties.getValueUsageType()).execute().getProperties().getEntity();
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
	public Collection<ENTITY> findMany(Properties properties) {
		return getPersistence().readMany(properties);
	}

	@Override
	public Collection<ENTITY> findMany() {
		//TODO use default settings like pagination and sorting
		return findMany(null);
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
	
}
