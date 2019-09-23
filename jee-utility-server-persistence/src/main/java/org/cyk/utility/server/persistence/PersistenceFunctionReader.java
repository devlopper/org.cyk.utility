package org.cyk.utility.server.persistence;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface PersistenceFunctionReader extends PersistenceFunction {

	PersistenceFunctionReader setAction(SystemAction action);
	
	PersistenceFunctionReader setEntityClass(Class<?> aClass);
	
	PersistenceFunctionReader setEntityIdentifier(Object identifier);
	
	PersistenceFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	
	PersistenceFunctionReader setQueryIdentifier(Object identifier);
	
	PersistenceFunctionReader setQueryValue(String value);
	
	PersistenceFunctionReader setQueryParameters(Properties parameters);
	
	PersistenceFunctionReader setQueryParameter(String name, Object value);
	
	/**/
	
	String MESSAGE_NOT_FOUND = "not found";
}
