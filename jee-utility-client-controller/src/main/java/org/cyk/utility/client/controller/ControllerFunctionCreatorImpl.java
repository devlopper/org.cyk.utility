package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.client.controller.data.DataRepresentationClassGetter;
import org.cyk.utility.client.controller.data.DataTransferObjectClassGetter;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;

public class ControllerFunctionCreatorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCreator , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Response __act__(SystemAction action, Object representation, Collection<?> dataTransferObjects) {
		if(representation instanceof RepresentationEntity) {
			return ((RepresentationEntity)representation).createOne(dataTransferObjects.iterator().next());
		}else
			__injectThrowableHelper__().throwRuntimeException("Data Representation of type "+representation.getClass()+" is not an instanceof RepresentationEntity");
		return null;
	}

	@Override
	protected ResponseEntityDto getResponseEntityDto(SystemAction action, Object representation, Response response) {
		return (ResponseEntityDto) response.readEntity(ResponseEntityDto.class);
	}
	/*
	@SuppressWarnings("rawtypes")
	@Override
	protected void __execute__(SystemAction action) {
		if(action!=null && __injectCollectionHelper__().isNotEmpty(action.getEntities())) {
			Class<?> entityClass = action.getEntities().getAt(0).getClass();
			Class<?> dataTransferClass = __inject__(DataTransferObjectClassGetter.class).setDataClass(entityClass).execute().getOutput();
			if(dataTransferClass == null)
				__injectThrowableHelper__().throwRuntimeException("Data Transfer Class is required for "+entityClass);
			Collection<?> dataTransferObjects = __injectInstanceHelper__().buildMany(dataTransferClass, action.getEntities().get());
			Class<?> dataRepresentationClass = __inject__(DataRepresentationClassGetter.class).setDataClass(entityClass).execute().getOutput();
			if(dataRepresentationClass == null)
				__injectThrowableHelper__().throwRuntimeException("Data Representation Class is required for "+entityClass);
			if(__injectClassHelper__().isInstanceOf(dataRepresentationClass, RepresentationEntity.class)) {
				RepresentationEntity representation = (RepresentationEntity) __inject__(ProxyGetter.class).setClazz(dataRepresentationClass).execute().getOutput();	
				//representation.createMany(dataTransferObjects);
				Response response = representation.createOne(dataTransferObjects.iterator().next());
				ResponseEntityDto responseEntityDto = (ResponseEntityDto) response.readEntity(ResponseEntityDto.class);
				if(responseEntityDto!=null) { 
					if(responseEntityDto.getStatus().equals(ResponseEntityDto.Status.FAILURE.name()))
						throw new RuntimeException(responseEntityDto.getMessageCollection().toString());
					//System.out.println("RESPONSE ENTITY : "+responseEntityDto);
				}
			}else
				__injectThrowableHelper__().throwRuntimeException("Data Representation Class of type "+dataRepresentationClass+" is not an instanceof RepresentationEntity");
		}
	}
	*/
	@Override
	public ControllerFunctionCreator addActionEntities(Object... entities) {
		return (ControllerFunctionCreator) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionCreator setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionCreator) super.setActionEntityClass(entityClass);
	}

	

}
