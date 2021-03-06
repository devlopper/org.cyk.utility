package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractBusinessFunctionReaderImpl extends AbstractBusinessFunctionImpl implements BusinessFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}
	
	@Override
	public BusinessFunctionReader setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}
	
	@Override
	public Object getEntityIdentifier() {
		return getProperties().getEntityIdentifier();
	}
	
	@Override
	public BusinessFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public ValueUsageType getEntityIdentifierValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}
	
	@Override
	public BusinessFunctionReader setAction(SystemAction action) {
		return (BusinessFunctionReader) super.setAction(action);
	}
	
	@Override
	public BusinessFunctionReader setEntity(Object entity) {
		return (BusinessFunctionReader) super.setEntity(entity);
	}
	
	@Override
	public BusinessFunctionReader setEntities(Collection<?> entities) {
		return (BusinessFunctionReader) super.setEntities(entities);
	}
}
