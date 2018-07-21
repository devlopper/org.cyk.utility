package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractBusinessEntityImpl<ENTITY> extends AbstractBusinessServiceProviderImpl<ENTITY> implements BusinessEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public ENTITY findOne(Object identifier, Properties properties) {
		return (ENTITY) __inject__(BusinessFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier).execute().getProperties().getEntity();
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

	@Override
	public Class<ENTITY> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
