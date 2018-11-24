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
	
	Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(SystemAction systemAction);
	
	WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder(SystemAction systemAction);
	
	Class<Form> getFormClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Form> getFormClass(SystemAction systemAction);
	
	Form injectForm(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Form injectForm(SystemAction systemAction);
	
	Class<Row> getRowClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Row> getRowClass(SystemAction systemAction);
	
	Row injectRow(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Row injectRow(SystemAction systemAction);
}