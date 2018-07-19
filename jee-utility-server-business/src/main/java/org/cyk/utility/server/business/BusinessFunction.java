package org.cyk.utility.server.business;

import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

public interface BusinessFunction extends SystemFunctionServer {
	
	BusinessFunction setEntityIdentifier(Object identifier);
	
	@Override BusinessFunction setAction(SystemAction action);
	
	@Override BusinessFunction setEntityClass(Class<?> aClass);
	
}
