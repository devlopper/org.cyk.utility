package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.TypeHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.MessageDto;
import org.cyk.utility.__kernel__.throwable.RuntimeExceptionDto;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityReader {

	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> entityClass,Arguments arguments);
	
	default <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> entityClass) {
		Arguments arguments = new Arguments();
		arguments.setControllerEntityClass(entityClass);
		return readMany(entityClass, arguments);
	}
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		
		@Override
		public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> entityClass,Arguments arguments) {
			if(entityClass == null)
				throw new RuntimeException("controller entity class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			Class<?> controllerEntityClass = arguments.getControllerEntityClass();
			if(controllerEntityClass == null)
				arguments.setControllerEntityClass(controllerEntityClass = entityClass);
			Class<?> representationEntityClass = arguments.getRepresentationEntityClass();
			if(representationEntityClass == null)
				representationEntityClass = RepresentationEntityClassGetter.getInstance().get(entityClass);	
			if(representationEntityClass == null)
				throw new RuntimeException("representation entity class is required");
			org.cyk.utility.__kernel__.representation.EntityReader entityReader = arguments.getRepresentationEntityReader();
			Boolean isRepresentationProxyable = arguments.getIsRepresentationProxyable();
			if(isRepresentationProxyable == null)
				isRepresentationProxyable = Boolean.TRUE;
			if(entityReader == null) {
				if(isRepresentationProxyable)
					entityReader = ProxyGetter.getInstance().get(org.cyk.utility.__kernel__.representation.EntityReader.class);
				else
					entityReader = org.cyk.utility.__kernel__.representation.EntityReader.getInstance();
			}
			if(entityReader == null)
				throw new RuntimeException("representation entity reader cannot be acquired");
			org.cyk.utility.__kernel__.representation.Arguments representationArguments = arguments.getRepresentationArguments();
			if(representationArguments == null)
				representationArguments = new org.cyk.utility.__kernel__.representation.Arguments();				
			if(StringHelper.isBlank(representationArguments.getRepresentationEntityClassName()))
				representationArguments.setRepresentationEntityClass(representationEntityClass);
			Response response = entityReader.read(representationArguments);
			arguments.setResponse(response);
			Collection<?> dtos = null;
			RuntimeExceptionDto runtimeExceptionDto = null;
			if(isRepresentationProxyable) {
				if(ResponseHelper.isFamilySuccessful(response))
					dtos = (Collection<?>) response.readEntity(TypeHelper.instantiateGenericCollectionParameterizedTypeForJaxrs(Collection.class,representationEntityClass));
				else
					runtimeExceptionDto = response.readEntity(RuntimeExceptionDto.class);
			}else {
				if(ResponseHelper.isFamilySuccessful(response))
					dtos = (Collection<?>) response.getEntity();
				else
					runtimeExceptionDto = (RuntimeExceptionDto) response.getEntity();
			}
			if(runtimeExceptionDto == null) {
				if(CollectionHelper.isEmpty(dtos))
					return null;
				Collection<ENTITY> collection = (Collection<ENTITY>) MappingHelper.getSources(dtos, controllerEntityClass);
				return collection;
			}else {
				if(CollectionHelper.isEmpty(runtimeExceptionDto.getMessages())) {
					
				}else {
					org.cyk.utility.__kernel__.throwable.RuntimeException runtimeException = new org.cyk.utility.__kernel__.throwable.RuntimeException();
					for(MessageDto message : runtimeExceptionDto.getMessages())
						runtimeException.addMessages(new org.cyk.utility.__kernel__.throwable.Message().setSummary(message.getSummary()).setDetails(message.getDetails()));
					throw runtimeException;
					/*
					for(MessageDto message : runtimeExceptionDto.getMessages())
						MessageRenderer.getInstance().render(new Message().setSeverity(Severity.ERROR).setSummary(message.getSummary()).setDetails(message.getDetails())
							, RenderType.GROWL);
					*/
				}
				return null;
			}
		}
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}