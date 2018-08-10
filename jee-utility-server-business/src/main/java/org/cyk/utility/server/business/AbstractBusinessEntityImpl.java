package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

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
		persistenceEntityClass = (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		persistence = (PERSISTENCE) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(getPersistenceEntityClass());
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
	
	@Override
	public BusinessEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType) {
		delete(getPersistence().readOne(identifier,valueUsageType));
		return this;
	}
	
	@Override
	public BusinessEntity<ENTITY> deleteBySystemIdentifier(Object identifier) {
		deleteByIdentifier(identifier,ValueUsageType.SYSTEM);
		return this;
	}
	
	@Override
	public BusinessEntity<ENTITY> deleteByBusinessIdentifier(Object identifier) {
		deleteByIdentifier(identifier,ValueUsageType.BUSINESS);
		return this;
	}
	
	@Override
	public BusinessServiceProvider<ENTITY> deleteAll() {
		__inject__(BusinessFunctionRemover.class).setAll(Boolean.TRUE).setEntityClass(getPersistenceEntityClass()).execute();
		return this;
	}
	
}
