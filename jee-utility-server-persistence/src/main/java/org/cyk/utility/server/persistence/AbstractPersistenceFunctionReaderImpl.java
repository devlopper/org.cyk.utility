package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionReaderImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getEnityFieldValue(Object entity, FieldName fieldName, ValueUsageType valueUsageType,String derivedFieldName) {
		if(getProperties().getEntity() == null){
			return getEntityIdentifier();
		}
		return super.getEnityFieldValue(entity, fieldName, valueUsageType, derivedFieldName);
	}
	
	@Override
	protected Collection<ValueUsageType> getValueUsageTypes(FieldName fieldName) {
		if(getProperties().getEntity() == null)
			return __inject__(CollectionHelper.class).instanciate(ValueUsageType.SYSTEM);
		return super.getValueUsageTypes(fieldName);
	}

	@Override
	public PersistenceFunctionReader setEntityClass(Class<?> aClass) {
		return (PersistenceFunctionReader) super.setEntityClass(aClass);
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
	public ValueUsageType getEntityIdentifierValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}
	
	@Override
	public PersistenceFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public PersistenceFunctionReader setNamedQueryIdentifier(Object identifier) {
		return (PersistenceFunctionReader) super.setNamedQueryIdentifier(identifier);
	}
}
