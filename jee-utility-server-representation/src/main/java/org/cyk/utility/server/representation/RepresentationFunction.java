package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;

import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

//TODO execution should follow these steps:
//1 - process
//2 - catch throwable if any
//3 - send response
public interface RepresentationFunction extends SystemFunctionServer {
	
	@Override RepresentationFunction execute();
	
	@Override RepresentationFunction setEntityIdentifier(Object identifier);
	
	@Override RepresentationFunction setEntityIdentifierValueUsageType(Object valueUsageType);
	
	@Override RepresentationFunction setAction(SystemAction action);
	
	@Override RepresentationFunction setEntityClass(Class<?> aClass);
	
	RepresentationFunction setResponse(Response response);
	Response getResponse();
	
	RepresentationFunction setPersistenceEntityClass(Class<?> aClass);
	Class<?> getPersistenceEntityClass();
}
