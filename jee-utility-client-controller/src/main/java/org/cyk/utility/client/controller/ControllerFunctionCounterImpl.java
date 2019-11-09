package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;

public class ControllerFunctionCounterImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCounter , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}

	@Override
	protected void __executeRepresentation__() {
		FilterDto filters = (FilterDto) Properties.getFromPath(getProperties(),Properties.FILTERS);
		if(__representation__ instanceof RepresentationEntity<?>)
			__response__ = ((RepresentationEntity<?>)__representation__).count(filters);	
		if(__response__!=null)
			setEntitiesCount(__response__.readEntity(Long.class));
	}
	
	@Override
	protected Boolean __isProcessReponse__() {
		return Boolean.FALSE;
	}
	
	@Override
	public ControllerFunctionCounter setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (ControllerFunctionCounter) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	@Override
	public ControllerFunctionCounter addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (ControllerFunctionCounter) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public ControllerFunctionCounter addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (ControllerFunctionCounter) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
}
