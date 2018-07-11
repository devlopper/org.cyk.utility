package org.cyk.utility.server.persistence;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public interface PersistenceFunctionReader extends PersistenceFunction {

	PersistenceFunctionReader setAction(SystemAction action);
	
	PersistenceFunctionReader setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
	PersistenceFunctionReader setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
	
	PersistenceFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	ValueUsageType getEntityIdentifierValueUsageType();
	
	/**/
	
	String MESSAGE_NOT_FOUND = "not found";
}
