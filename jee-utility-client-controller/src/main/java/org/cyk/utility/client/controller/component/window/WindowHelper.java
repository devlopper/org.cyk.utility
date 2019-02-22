package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.helper.Helper;
import org.cyk.utility.system.action.SystemAction;

public interface WindowHelper extends Helper {

	Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(SystemAction systemAction);
	
	WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder(SystemAction systemAction);
	
}
