package org.cyk.utility.client.controller;

import java.util.Collection;

public interface ControllerFunctionRedirector extends ControllerFunction {

	ControllerFunctionRedirector setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	ControllerFunctionRedirector addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	ControllerFunctionRedirector addActionEntitiesIdentifiers(Object...entitiesIdentifiers);
	
}
