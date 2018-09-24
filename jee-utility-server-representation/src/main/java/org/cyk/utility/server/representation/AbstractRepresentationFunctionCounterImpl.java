package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCount;

public abstract class AbstractRepresentationFunctionCounterImpl extends AbstractRepresentationFunctionImpl implements RepresentationFunctionCounter, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCount.class));
	}
	
	@Override
	public RepresentationFunctionCounter setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}
	
	@Override
	public RepresentationFunctionCounter setEntityIdentifierValueUsageType(Object valueUsageType) {
		return (RepresentationFunctionCounter) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public RepresentationFunctionCounter setAction(SystemAction action) {
		return (RepresentationFunctionCounter) super.setAction(action);
	}
	
	@Override
	public RepresentationFunctionCounter setEntity(Object entity) {
		return (RepresentationFunctionCounter) super.setEntity(entity);
	}
	
	@Override
	public RepresentationFunctionCounter setEntities(Collection<?> entities) {
		return (RepresentationFunctionCounter) super.setEntities(entities);
	}
}
