package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.system.action.SystemAction;

public interface WindowContainerManagedWindowBuilderGetter extends FunctionWithPropertiesAsInput<WindowContainerManagedWindowBuilder> {

	SystemAction getSystemAction();
	WindowContainerManagedWindowBuilderGetter setSystemAction(SystemAction systemAction);
	
}
