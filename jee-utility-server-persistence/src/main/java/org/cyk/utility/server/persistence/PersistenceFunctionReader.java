package org.cyk.utility.server.persistence;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public interface PersistenceFunctionReader extends PersistenceFunction {

	PersistenceFunctionReader setAction(SystemAction action);
	
	PersistenceFunctionReader setEntityClass(Class<?> aClass);
	
	PersistenceFunctionReader setEntityIdentifier(Object identifier);
	
	PersistenceFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	
	PersistenceFunctionReader setNamedQueryIdentifier(Object identifier);
	
	/**/
	
	String MESSAGE_NOT_FOUND = "not found";
}
