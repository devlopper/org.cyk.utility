package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface ResponseProcessor {

	void process(Response response,Arguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ResponseProcessor,Serializable {
		
		@Override
		public void process(Response response,Arguments arguments) {
			/*if(arguments == null)
				throw new IllegalArgumentException("arguments are required");
			arguments.setResponse(response);
			Collection<?> dtos = null;
			RuntimeExceptionDto runtimeException = null;
			if(isRepresentationProxyable) {
				if(ResponseHelper.isFamilySuccessful(response))
					dtos = (Collection<?>) response.readEntity(TypeHelper.instantiateGenericCollectionParameterizedTypeForJaxrs(Collection.class,representationEntityClass));
				else
					runtimeException = response.readEntity(RuntimeExceptionDto.class);
			}else {
				if(ResponseHelper.isFamilySuccessful(response))
					dtos = (Collection<?>) response.getEntity();
				else
					runtimeException = (RuntimeExceptionDto) response.getEntity();
			}
			if(runtimeException == null) {
				if(CollectionHelper.isEmpty(dtos))
					return null;
				Collection<ENTITY> collection = (Collection<ENTITY>) MappingHelper.getSources(dtos, controllerEntityClass);
				return collection;
			}else {
				if(CollectionHelper.isEmpty(runtimeException.getMessages())) {
					
				}else { 
					for(MessageDto message : runtimeException.getMessages())
						MessageRenderer.getInstance().render(new Message().setSeverity(Severity.ERROR).setSummary(message.getSummary()).setDetails(message.getDetails())
							, RenderType.GROWL);
				}
				return null;
			}*/
		}
	}
	
	/**/
	
	static ResponseProcessor getInstance() {
		return Helper.getInstance(ResponseProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
