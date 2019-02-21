package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ResponseBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Response> implements ResponseBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object entity;
	private ResponseEntityDtoBuilder responseEntityDto;
	private Status status;
	
	@Override
	protected Response __execute__() throws Exception {
		Response response = null;
		Object entity = getEntity();
		ResponseEntityDtoBuilder responseEntityDto = getResponseEntityDto();
		if(entity == null) {
			if(responseEntityDto!=null)
				entity = responseEntityDto.execute().getOutput();
		}
		Status status = getStatus();
		if(status == null) {
			if(responseEntityDto!=null) {
				if(responseEntityDto.getThrowable()!=null)
					status = Status.INTERNAL_SERVER_ERROR;
			}
		}
		response = Response.status(status).entity(entity).build();
		return response;
	}
	
	@Override
	public Object getEntity() {
		return entity;
	}
	
	@Override
	public ResponseBuilder setEntity(Object entity) {
		this.entity = entity;
		return this;
	}
	
	@Override
	public ResponseEntityDtoBuilder getResponseEntityDto() {
		return responseEntityDto;
	}
	
	@Override
	public ResponseBuilder setResponseEntityDto(ResponseEntityDtoBuilder responseEntityDto) {
		this.responseEntityDto = responseEntityDto;
		return this;
	}
	
	public ResponseBuilder setStatus(Status status) {
		this.status = status;
		return this;
	}
	
	@Override
	public Status getStatus() {
		return status;
	}
	
	
}
