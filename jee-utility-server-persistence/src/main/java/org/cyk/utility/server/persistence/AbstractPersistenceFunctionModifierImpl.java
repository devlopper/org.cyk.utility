package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionModifierImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public PersistenceFunctionModifier setEntity(Object entity) {
		return (PersistenceFunctionModifier) super.setEntity(entity);
	}
	
	@Override
	public PersistenceFunctionModifier setEntities(Collection<?> entities) {
		return (PersistenceFunctionModifier) super.setEntities(entities);
	}
	
	@Override
	public PersistenceFunctionModifier setAction(SystemAction action) {
		return (PersistenceFunctionModifier) super.setAction(action);
	}
	
	@Override
	public PersistenceFunctionModifier setEntityClass(Class<?> aClass) {
		return (PersistenceFunctionModifier) super.setEntityClass(aClass);
	}
	
	@Override
	public PersistenceFunctionModifier setEntityIdentifier(Object identifier) {
		return (PersistenceFunctionModifier) super.setEntityIdentifier(identifier);
	}

	@Override
	public PersistenceFunctionModifier setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		return (PersistenceFunctionModifier) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public PersistenceFunctionModifier setQueryIdentifier(Object identifier) {
		return (PersistenceFunctionModifier) super.setQueryIdentifier(identifier);
	}
	
	@Override
	public PersistenceFunctionModifier setQueryParameter(String name, Object value) {
		return (PersistenceFunctionModifier) super.setQueryParameter(name, value);
	}
	
	@Override
	public PersistenceFunctionModifier setQueryParameters(Properties parameters) {
		return (PersistenceFunctionModifier) super.setQueryParameters(parameters);
	}
	
}
