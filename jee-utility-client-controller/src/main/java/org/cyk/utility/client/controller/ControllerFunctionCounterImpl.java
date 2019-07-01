package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;

public class ControllerFunctionCounterImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCounter , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}

	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response;
		Object filters = Properties.getFromPath(getProperties(),Properties.FILTERS);
		String filtersAsString = null;
		if(filters instanceof String)
			filtersAsString = (String) filters;
		response = representation.count(filtersAsString);
		if(response!=null)
			setEntitiesCount(response.readEntity(Long.class));
		return response;
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
