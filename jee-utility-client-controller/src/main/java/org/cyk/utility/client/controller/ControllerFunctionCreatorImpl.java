package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.exception.ServiceNotFoundException;

public class ControllerFunctionCreatorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCreator , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response = null;
		response = representation.createOne(dataTransferObjects.iterator().next());
		if(Boolean.TRUE.equals(__inject__(ResponseHelper.class).isStatusClientErrorNotFound(response))) {
			__injectThrowableHelper__().throw_(__inject__(ServiceNotFoundException.class).setSystemAction(action).setResponse(response));
		}
		Object identifier = response.getHeaderString("entity-identifier");
		//TODO identifier should converted to its corresponding type before setting
		__injectFieldHelper__().setFieldValueSystemIdentifier(action.getEntities().get().iterator().next(), identifier); 
		return response;
	}
	
	@Override
	public ControllerFunctionCreator addActionEntities(Object... entities) {
		return (ControllerFunctionCreator) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionCreator setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionCreator) super.setActionEntityClass(entityClass);
	}

	

}
