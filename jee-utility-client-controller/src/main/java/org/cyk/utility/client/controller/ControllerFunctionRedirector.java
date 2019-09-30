package org.cyk.utility.client.controller;

import java.util.Collection;

import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface ControllerFunctionRedirector extends ControllerFunction {

	ControllerFunctionRedirector setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	ControllerFunctionRedirector addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	ControllerFunctionRedirector addActionEntitiesIdentifiers(Object...entitiesIdentifiers);

	SystemAction getTargetSystemAction();
	ControllerFunctionRedirector setTargetSystemAction(SystemAction targetSystemAction);
	
}
