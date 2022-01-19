package org.cyk.utility.service.server.exception;
import java.io.Serializable;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public abstract class AbstractRuntimeExceptionMapper<T extends RuntimeException> implements javax.ws.rs.ext.ExceptionMapper<T>,Serializable {

	@Override
	public Response toResponse(T exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}

}