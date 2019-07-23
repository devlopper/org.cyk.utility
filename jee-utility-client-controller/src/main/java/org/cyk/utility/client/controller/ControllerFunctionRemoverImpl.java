package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.type.BooleanHelper;

public class ControllerFunctionRemoverImpl extends AbstractControllerFunctionImpl implements ControllerFunctionRemover , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Boolean isAll = getProperties().getAll() == null ? Boolean.FALSE : __inject__(BooleanHelper.class).get(getProperties().getAll());
		Response response = null;
		if(Boolean.TRUE.equals(isAll))
			response = representation.deleteAll();
		else
			response = representation.deleteOne(dataTransferObjects.iterator().next());
		//Object identifierType = (String) getProperty(Properties.VALUE_USAGE_TYPE);
		//if(Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entities__)))
		//	;
		
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
