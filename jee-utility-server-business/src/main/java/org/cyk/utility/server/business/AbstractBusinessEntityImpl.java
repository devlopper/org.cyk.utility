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

	@Getter private Class<ENTITY> entityClass;
	@Getter private PERSISTENCE persistence;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		persistence = (PERSISTENCE) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(getEntityClass());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ENTITY findOne(Object identifier, Properties properties) {
		return (ENTITY) __inject__(BusinessFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier)
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findMany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**/
	
}
