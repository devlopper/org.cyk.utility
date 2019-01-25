package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.system.action.SystemAction;

public interface WindowContainerManagedWindowBuilder extends FunctionWithPropertiesAsInput<WindowBuilder> {

	WindowBuilder getWindow();
	WindowBuilder getWindow(Boolean injectIfNull);
	WindowContainerManagedWindowBuilder setWindow(WindowBuilder window);
	
	MenuBuilderMap getMenuMap();
	MenuBuilderMap getMenuMap(Boolean injectIfNull);
	WindowContainerManagedWindowBuilder setMenuMap(MenuBuilderMap menuMap);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	WindowContainerManagedWindowBuilder setView(ViewBuilder view);
	
	SystemAction getSystemAction();
	WindowContainerManagedWindowBuilder setSystemAction(SystemAction systemAction);
	
	Class<? extends Form> getFormClass();
	Class<? extends Form> getFormClass(Boolean getFromRequestIfNull);
	WindowContainerManagedWindowBuilder setFormClass(Class<? extends Form> formClass);
	
	Class<? extends Row> getRowClass();
	Class<? extends Row> getRowClass(Boolean getFromRequestIfNull);
	WindowContainerManagedWindowBuilder setRowClass(Class<? extends Row> rowClass);
	
	WindowContainerManaged getWindowContainerManaged();
	WindowContainerManagedWindowBuilder setWindowContainerManaged(WindowContainerManaged windowContainerManaged);

	WindowRenderType getWindowRenderType();
	WindowContainerManagedWindowBuilder setWindowRenderType(WindowRenderType windowRenderType);
	
	Object getRequest();
	WindowContainerManagedWindowBuilder setRequest(Object request);
	
	Object getContext();
	WindowContainerManagedWindowBuilder setContext(Object context);
}