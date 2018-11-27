package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.system.AbstractSystemFunctionClientImpl;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;

public abstract class AbstractControllerFunctionImpl extends AbstractSystemFunctionClientImpl implements ControllerFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ControllerFunction setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunction) super.setActionEntityClass(entityClass);
	}
	
	@Override
	public ControllerFunction addActionEntities(Object... entities) {
		return (ControllerFunction) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunction setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (ControllerFunction) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	@Override
	public ControllerFunction addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (ControllerFunction) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public ControllerFunction addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (ControllerFunction) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerController.class);
	}
	
}
