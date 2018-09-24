package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionReaderImpl extends AbstractRepresentationFunctionImpl implements RepresentationFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}
	
	@Override
	public RepresentationFunctionReader setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}
	
	@Override
	public Object getEntityIdentifier() {
		return getProperties().getEntityIdentifier();
	}
	
	@Override
	public RepresentationFunctionReader setEntityIdentifierValueUsageType(Object valueUsageType) {
		return (RepresentationFunctionReader) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public ValueUsageType getEntityIdentifierValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}
	
	@Override
	public RepresentationFunctionReader setAction(SystemAction action) {
		return (RepresentationFunctionReader) super.setAction(action);
	}
	
	@Override
	public RepresentationFunctionReader setEntity(Object entity) {
		return (RepresentationFunctionReader) super.setEntity(entity);
	}
	
	@Override
	public RepresentationFunctionReader setEntities(Collection<?> entities) {
		return (RepresentationFunctionReader) super.setEntities(entities);
	}
}
