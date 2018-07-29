package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionTransactionImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean __executeGetIsExecutable__(Boolean value) {
		Object queryIdentifier = getQueryIdentifier();
		return queryIdentifier == null ? getEntity() != null : Boolean.TRUE;
	}
	
	@Override
	public PersistenceFunctionTransaction setEntity(Object entity) {
		return (PersistenceFunctionTransaction) super.setEntity(entity);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntities(Collection<?> entities) {
		return (PersistenceFunctionTransaction) super.setEntities(entities);
	}
	
	@Override
	public PersistenceFunctionTransaction setAction(SystemAction action) {
		return (PersistenceFunctionTransaction) super.setAction(action);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntityClass(Class<?> aClass) {
		return (PersistenceFunctionTransaction) super.setEntityClass(aClass);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntityIdentifier(Object identifier) {
		return (PersistenceFunctionTransaction) super.setEntityIdentifier(identifier);
	}

	@Override
	public PersistenceFunctionTransaction setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		return (PersistenceFunctionTransaction) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public PersistenceFunctionTransaction setQueryIdentifier(Object identifier) {
		return (PersistenceFunctionTransaction) super.setQueryIdentifier(identifier);
	}
	
	@Override
	public PersistenceFunctionTransaction setQueryParameter(String name, Object value) {
		return (PersistenceFunctionTransaction) super.setQueryParameter(name, value);
	}
	
	@Override
	public PersistenceFunctionTransaction setQueryParameters(Properties parameters) {
		return (PersistenceFunctionTransaction) super.setQueryParameters(parameters);
	}
}
