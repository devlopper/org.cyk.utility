package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;

public interface RepresentationFunctionReader extends RepresentationFunction {

	RepresentationFunctionReader setEntityIdentifier(Object identifier);
	
	RepresentationFunctionReader setEntityIdentifierValueUsageType(Object valueUsageType);
	
	RepresentationFunctionReader setEntity(Object entity);
	
	RepresentationFunctionReader setEntities(Collection<?> entities);
	
	RepresentationFunctionReader setAction(SystemAction action);
	
}
