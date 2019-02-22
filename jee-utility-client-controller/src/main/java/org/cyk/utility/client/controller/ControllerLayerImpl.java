package org.cyk.utility.client.controller;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.system.layer.SystemLayerController;

@Singleton
public class ControllerLayerImpl extends AbstractSingleton implements ControllerLayer,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		Class<ControllerEntity<ENTITY>> aClass = (Class<ControllerEntity<ENTITY>>) __inject__(SystemLayerController.class).getInterfaceClassFromEntityClassName(entityClass);
		return aClass;
	}
	
	@Override
	public <ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		Class<ControllerEntity<ENTITY>> aClass = getInterfaceClassFromEntityClass(entityClass);
		ControllerEntity<ENTITY> persistence = null;
		if(aClass == null)
			System.err.println("No controller interface found for controller entity class "+entityClass);
		else
			persistence = (ControllerEntity<ENTITY>) __inject__(SystemLayerController.class).injectInterfaceClassFromEntityClassName(entityClass);
		return persistence;
	}

	@Override
	public <ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntity(ENTITY entity) {
		return entity == null ? null : getInterfaceClassFromEntityClass((Class<ENTITY>)entity.getClass());
	}

	@Override
	public <ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntity(ENTITY entity) {
		return entity == null ? null : (ControllerEntity<ENTITY>) injectInterfaceClassFromEntityClass(entity.getClass());
	}

	@Override
	public Class<?> getDataTransferClassFromEntityClass(Class<?> entityClass) {
		return __inject__(SystemLayerController.class).getDataTransferClassFromEntityClass(entityClass);
	}
	
	@Override
	public Class<?> getDataTransferClassFromEntity(Object entity) {
		return __inject__(SystemLayerController.class).getDataTransferClassFromEntity(entity);
	}
	
	@Override
	public Class<?> getDataRepresentationClassFromEntityClass(Class<?> entityClass) {
		return __inject__(SystemLayerController.class).getRepresentationClassFromEntityClass(entityClass);
	}
	
	@Override
	public Class<?> getDataRepresentationClassFromEntity(Object entity) {
		return __inject__(SystemLayerController.class).getRepresentationClassFromEntity(entity);
	}
	
	
	/**/
	
}
