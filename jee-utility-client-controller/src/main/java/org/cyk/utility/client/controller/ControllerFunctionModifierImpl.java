package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.exception.ServiceNotFoundException;

public class ControllerFunctionModifierImpl extends AbstractControllerFunctionImpl implements ControllerFunctionModifier , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}
	
	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response = null;
		String fields = (String) getProperty(Properties.FIELDS);//TODO get logic from centralized function
		response = representation.updateOne(dataTransferObjects.iterator().next(),fields);
		if(Boolean.TRUE.equals(__injectResponseHelper__().isStatusClientErrorNotFound(response))) {
			__injectThrowableHelper__().throw_(__inject__(ServiceNotFoundException.class).setSystemAction(action).setResponse(response));
		}			
		return response;
	}
	
	
	@Override
	public ControllerFunctionModifier addActionEntities(Object... entities) {
		return (ControllerFunctionModifier) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionModifier setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionModifier) super.setActionEntityClass(entityClass);
	}

}
