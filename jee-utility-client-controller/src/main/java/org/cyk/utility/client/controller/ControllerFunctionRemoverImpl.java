package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.exception.ServiceNotFoundException;

public class ControllerFunctionRemoverImpl extends AbstractControllerFunctionImpl implements ControllerFunctionRemover , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response = null;
		//Object identifierType = (String) getProperty(Properties.VALUE_USAGE_TYPE);
		response = representation.deleteOne(__injectFieldHelper__().getFieldValueBusinessIdentifier(dataTransferObjects.iterator().next()).toString(),"business");
		if(Boolean.TRUE.equals(__injectResponseHelper__().isStatusClientErrorNotFound(response))) {
			__injectThrowableHelper__().throw_(__inject__(ServiceNotFoundException.class).setSystemAction(action).setResponse(response));
		}			
		return response;
	}
	
	@Override
	public ControllerFunctionRemover addActionEntities(Object... entities) {
		return (ControllerFunctionRemover) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionRemover setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionRemover) super.setActionEntityClass(entityClass);
	}

}
