package org.cyk.utility.system.exception;

import java.io.Serializable;

import javax.ws.rs.core.Response;

public abstract class AbstractSystemClientExceptionImpl extends AbstractSystemExceptionImpl implements SystemClientException,Serializable {
	private static final long serialVersionUID = 1L;

	private Response response;
	
	@Override
	public Response getResponse() {
		return response;
	}
	
	@Override
	public SystemClientException setResponse(Response response) {
		this.response = response;
		return this;
	}
	
}
