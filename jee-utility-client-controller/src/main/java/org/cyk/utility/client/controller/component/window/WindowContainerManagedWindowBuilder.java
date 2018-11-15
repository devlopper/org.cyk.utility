package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
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
	WindowContainerManagedWindowBuilder setSystemAction(SystemAction systemAction);
	
}