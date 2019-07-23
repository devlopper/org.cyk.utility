package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.server.representation.Constant;
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
		Response response = null;
		response = representation.createOne(dataTransferObjects.iterator().next());
		return response;
	}
	
	@Override
	protected void __listenExecuteServiceFound__() {
		super.__listenExecuteServiceFound__();
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__entities__))) {
			String[] systemIdentifiers = StringUtils.split(__response__.getHeaderString(Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_SYSTEM),",");
			String[] businessIdentifiers = StringUtils.split(__response__.getHeaderString(Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_BUSINESS),",");
			Integer count = 0;
			for(Object index : __entities__) {
				if(__entityClassSystemIdentifierField__ != null)
					__inject__(FieldValueSetter.class).execute(index, __entityClassSystemIdentifierField__, systemIdentifiers[count]);
				if(__entityClassBusinessIdentifierField__ != null)
					__inject__(FieldValueSetter.class).execute(index, __entityClassBusinessIdentifierField__, businessIdentifiers[count]);
				count++;
			}	
		}
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
