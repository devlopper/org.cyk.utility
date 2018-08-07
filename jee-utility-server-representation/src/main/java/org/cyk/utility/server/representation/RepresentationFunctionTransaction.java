package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;

public interface RepresentationFunctionTransaction extends RepresentationFunction {

	RepresentationFunctionTransaction setEntity(Object entity);
	
	RepresentationFunctionTransaction setEntities(Collection<?> entities);
	
	RepresentationFunctionTransaction setAction(SystemAction action);
}
