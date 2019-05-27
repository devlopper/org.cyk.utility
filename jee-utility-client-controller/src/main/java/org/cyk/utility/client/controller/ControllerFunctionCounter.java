package org.cyk.utility.client.controller;

import java.util.Collection;

public interface ControllerFunctionCounter extends ControllerFunction {

	ControllerFunctionCounter setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	ControllerFunctionCounter addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	ControllerFunctionCounter addActionEntitiesIdentifiers(Object...entitiesIdentifiers);
	
}
