package org.cyk.utility.client.controller;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.system.action.SystemAction;

public interface ControllerLayer extends Singleton {
	
	<ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	<ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	
	<ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntity(ENTITY entity);
	<ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntity(ENTITY entity);
	
	Class<WindowContainerManagedWindowBuilder> getWindowBuilderClassFromEntityClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<WindowContainerManagedWindowBuilder> getWindowBuilderClassFromEntityClass(Class<?> entityClass,SystemAction systemAction);
	
	WindowContainerManagedWindowBuilder injectWindowBuilderClassFromEntityClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	WindowContainerManagedWindowBuilder injectWindowBuilderClassFromEntityClass(Class<?> entityClass,SystemAction systemAction);
	
	Class<Form> getFormClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Form> getFormClass(Class<?> entityClass,SystemAction systemAction);
	
	Form injectForm(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Form injectForm(Class<?> entityClass,SystemAction systemAction);
	
	Class<Row> getRowClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Row> getRowClass(Class<?> entityClass,SystemAction systemAction);
	
	Row injectRow(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Row injectRow(Class<?> entityClass,SystemAction systemAction);
}