package org.cyk.utility.client.controller;

public interface ControllerFunctionCreator extends ControllerFunction {

	ControllerFunctionCreator setActionEntityClass(Class<?> entityClass);
	ControllerFunctionCreator addActionEntities(Object...entities);
	
}
