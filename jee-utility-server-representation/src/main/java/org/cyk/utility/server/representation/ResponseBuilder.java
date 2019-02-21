package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ResponseBuilder extends FunctionWithPropertiesAsInput<Response> {

	Object getEntity();
	ResponseBuilder setEntity(Object entity);
	
	ResponseEntityDtoBuilder getResponseEntityDto();
	ResponseBuilder setResponseEntityDto(ResponseEntityDtoBuilder responseEntityDto);
	
	Status getStatus();
	ResponseBuilder setStatus(Status status);
	
}
