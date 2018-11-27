package org.cyk.utility.client.controller;

import java.util.Collection;

public interface ControllerFunctionReader extends ControllerFunction {

	ControllerFunctionReader setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	ControllerFunctionReader addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	ControllerFunctionReader addActionEntitiesIdentifiers(Object...entitiesIdentifiers);
	
}
