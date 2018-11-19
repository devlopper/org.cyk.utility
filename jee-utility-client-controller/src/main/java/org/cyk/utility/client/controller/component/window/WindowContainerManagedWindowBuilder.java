package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.system.action.SystemAction;

public interface WindowContainerManagedWindowBuilder extends FunctionWithPropertiesAsInput<WindowBuilder> {

	MenuBuilderMap getMenuMap();
	MenuBuilderMap getMenuMap(Boolean injectIfNull);
	WindowContainerManagedWindowBuilder setMenuMap(MenuBuilderMap menuMap);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	WindowContainerManagedWindowBuilder setView(ViewBuilder view);
	
	SystemAction getSystemAction();
	SystemAction getSystemAction(Boolean getFromRequestIfNull);
	WindowContainerManagedWindowBuilder setSystemAction(SystemAction systemAction);
	
	Class<?> getEntityClass();
	Class<?> getEntityClass(Boolean getFromRequestIfNull);
	WindowContainerManagedWindowBuilder setEntityClass(Class<?> entityClass);
	
	Class<? extends Form> getFormClass();
	Class<? extends Form> getFormClass(Boolean getFromRequestIfNull);
	WindowContainerManagedWindowBuilder setFormClass(Class<? extends Form> formClass);
	
	Class<? extends Row> getRowClass();
	Class<? extends Row> getRowClass(Boolean getFromRequestIfNull);
	WindowContainerManagedWindowBuilder setRowClass(Class<? extends Row> rowClass);
}