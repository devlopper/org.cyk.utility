package org.cyk.utility.rest;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class RequestExecutorImpl implements RequestExecutor,Serializable {

	@Override
	public Response execute(Request request) {
		if(request == null)
			throw new RuntimeException("Request is required");		
		try {
			ResponseBuilder.Arguments responseBuilderArguments = request.execute();
			return ResponseBuilder.getInstance().build(responseBuilderArguments);
		} catch (Exception exception) {
			return ResponseBuilder.getInstance().build(exception);
		}
	}
}