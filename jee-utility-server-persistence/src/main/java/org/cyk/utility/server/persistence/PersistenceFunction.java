package org.cyk.utility.server.persistence;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemFunctionServer;
import org.cyk.utility.system.action.SystemAction;

public interface PersistenceFunction extends SystemFunctionServer {

	PersistenceFunction setAction(SystemAction action);
	
	PersistenceFunction setEntityClass(Class<?> aClass);
	
	PersistenceFunction setQueryIdentifier(Object identifier);
	Object getQueryIdentifier();
	
	PersistenceFunction setQueryParameters(Properties parameters);
	Properties getQueryParameters();
	PersistenceFunction setQueryParameter(String name,Object value);
}
