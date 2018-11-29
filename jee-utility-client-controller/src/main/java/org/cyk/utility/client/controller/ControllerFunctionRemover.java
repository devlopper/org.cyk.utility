package org.cyk.utility.client.controller;

public interface ControllerFunctionRemover extends ControllerFunction {

	ControllerFunctionRemover setActionEntityClass(Class<?> entityClass);
	ControllerFunctionRemover addActionEntities(Object...entities);
	
}
