package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;

import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

public interface RepresentationFunction extends SystemFunctionServer {
	
	@Override RepresentationFunction execute();
	
	RepresentationFunction setEntityIdentifier(Object identifier);
	
	@Override RepresentationFunction setAction(SystemAction action);
	
	@Override RepresentationFunction setEntityClass(Class<?> aClass);
	
	RepresentationFunction setResponse(Response response);
	Response getResponse();
	
}
