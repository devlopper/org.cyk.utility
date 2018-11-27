package org.cyk.utility.client.controller;

import java.util.Collection;

import org.cyk.utility.system.SystemFunctionClient;

public interface ControllerFunction extends SystemFunctionClient {

	ControllerFunction setActionEntityClass(Class<?> entityClass);
	ControllerFunction addActionEntities(Object...entities);
	
	ControllerFunction setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	ControllerFunction addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	ControllerFunction addActionEntitiesIdentifiers(Object...entitiesIdentifiers);
}
