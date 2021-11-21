package org.cyk.utility.business.server;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.business.RequestException;

@javax.ws.rs.ext.Provider
public class RequestExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<RequestException> {

	@Override
	public Response toResponse(RequestException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}

}