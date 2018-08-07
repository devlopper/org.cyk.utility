package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public interface RepresentationFunctionReader extends RepresentationFunction {

	RepresentationFunctionReader setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
	
	RepresentationFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType);
	ValueUsageType getEntityIdentifierValueUsageType();
	
	RepresentationFunctionReader setEntity(Object entity);
	
	RepresentationFunctionReader setEntities(Collection<?> entities);
	
	RepresentationFunctionReader setAction(SystemAction action);
	
}
