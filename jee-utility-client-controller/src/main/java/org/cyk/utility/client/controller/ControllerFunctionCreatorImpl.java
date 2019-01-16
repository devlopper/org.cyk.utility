package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;

public class ControllerFunctionCreatorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCreator , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		return representation.createOne(dataTransferObjects.iterator().next());
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
