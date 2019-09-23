package org.cyk.utility.server.business;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface BusinessFunctionReader extends BusinessFunction {

	BusinessFunctionReader setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
	
	BusinessFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	ValueUsageType getEntityIdentifierValueUsageType();
	
	BusinessFunctionReader setEntity(Object entity);
	
	BusinessFunctionReader setEntities(Collection<?> entities);
	
	BusinessFunctionReader setAction(SystemAction action);
	
}
