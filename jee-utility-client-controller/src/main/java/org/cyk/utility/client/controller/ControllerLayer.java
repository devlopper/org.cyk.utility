package org.cyk.utility.client.controller;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface ControllerLayer extends Singleton {
	
	<ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	<ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	
	<ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntity(ENTITY entity);
	<ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntity(ENTITY entity);
	
	Class<?> getDataTransferClassFromEntityClass(Class<?> entityClass);
	Class<?> getDataTransferClassFromEntity(Object entity);
	
	Class<?> getDataRepresentationClassFromEntityClass(Class<?> entityClass);
	Class<?> getDataRepresentationClassFromEntity(Object entity);
	
}