package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractPersistenceFunctionReaderImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;

	/*@Override
	protected Object getEnityFieldValue(Object entity, FieldName fieldName, ValueUsageType valueUsageType,String derivedFieldName) {
		if(getProperties().getEntity() == null){
			return getEntityIdentifier();
		}
		return super.getEnityFieldValue(entity, fieldName, valueUsageType, derivedFieldName);
	}*/
	
	@Override
	protected Collection<ValueUsageType> getValueUsageTypes(FieldName fieldName) {
		if(getProperties().getEntity() == null)
			return List.of(ValueUsageType.SYSTEM);
		return super.getValueUsageTypes(fieldName);
	}

	@Override
	public PersistenceFunctionReader setEntityClass(Class<?> aClass) {
		super.setEntityClass(aClass);
		if(getQueryResultClass() == null)
			setQueryResultClass(getEntityClass());
		return this;
	}

	@Override
	public PersistenceFunctionReader setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}

	@Override
	public Object getEntityIdentifier() {
		return getProperties().getEntityIdentifier();
	}

	@Override
	public PersistenceFunctionReader setAction(SystemAction action) {
		return (PersistenceFunctionReader) super.setAction(action);
	}
	
	@Override
	public PersistenceFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public PersistenceFunctionReader setQueryIdentifier(Object identifier) {
		return (PersistenceFunctionReader) super.setQueryIdentifier(identifier);
	}
	
	@Override
	public PersistenceFunctionReader setQueryValue(String value) {
		return (PersistenceFunctionReader) super.setQueryValue(value);
	}
	
	@Override
	public PersistenceFunctionReader setQueryParameters(Properties parameters) {
		return (PersistenceFunctionReader) super.setQueryParameters(parameters);
	}
	
	@Override
	public PersistenceFunctionReader setQueryParameter(String name, Object value) {
		return (PersistenceFunctionReader) super.setQueryParameter(name, value);
	}
}
