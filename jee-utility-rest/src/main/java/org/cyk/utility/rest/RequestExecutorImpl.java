package org.cyk.utility.rest;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class RequestExecutorImpl implements RequestExecutor,Serializable {

	@Override
	public Response execute(Request request,Boolean isThrowableHandlable) {
		if(request == null)
			throw new RuntimeException("Request is required");	
		if(Boolean.TRUE.equals(isThrowableHandlable))
			try {
				return ResponseBuilder.getInstance().build(request.execute());
			} catch (Exception exception) {
				exception.printStackTrace();
				return ResponseBuilder.getInstance().build(exception);
			}
		return ResponseBuilder.getInstance().build(request.execute());
	}
	
	@Override
	public Response execute(Request request) {
		return execute(request, Boolean.TRUE);
	}
}