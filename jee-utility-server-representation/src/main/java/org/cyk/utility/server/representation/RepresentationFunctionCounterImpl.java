package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.system.action.SystemAction;

public class RepresentationFunctionCounterImpl extends AbstractRepresentationFunctionCounterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		ResponseBuilder responseBuilder = null;
		Long count = __injectBusiness__().count(getPersistenceEntityClass()/*, properties*/);
		responseBuilder = Response.status(Response.Status.OK).entity(count);
		setResponse(responseBuilder.build());
	}

}
