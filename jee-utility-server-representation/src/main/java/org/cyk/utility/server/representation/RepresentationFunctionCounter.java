package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface RepresentationFunctionCounter extends RepresentationFunction {

	RepresentationFunctionCounter setEntityIdentifier(Object identifier);
	
	RepresentationFunctionCounter setEntityIdentifierValueUsageType(Object valueUsageType);
	
	RepresentationFunctionCounter setEntity(Object entity);
	
	RepresentationFunctionCounter setEntities(Collection<?> entities);
	
	RepresentationFunctionCounter setAction(SystemAction action);
	
}
