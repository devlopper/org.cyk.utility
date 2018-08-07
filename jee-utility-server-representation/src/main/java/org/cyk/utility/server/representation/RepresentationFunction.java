package org.cyk.utility.server.representation;

import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

public interface RepresentationFunction extends SystemFunctionServer {
	
	RepresentationFunction setEntityIdentifier(Object identifier);
	
	@Override RepresentationFunction setAction(SystemAction action);
	
	@Override RepresentationFunction setEntityClass(Class<?> aClass);
	
}
