package org.cyk.utility.client.controller;

public interface ControllerFunctionModifier extends ControllerFunction {

	ControllerFunctionModifier setActionEntityClass(Class<?> entityClass);
	ControllerFunctionModifier addActionEntities(Object...entities);
	
}
