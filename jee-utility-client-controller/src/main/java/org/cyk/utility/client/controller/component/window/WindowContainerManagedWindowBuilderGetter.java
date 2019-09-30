package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface WindowContainerManagedWindowBuilderGetter extends FunctionWithPropertiesAsInput<WindowContainerManagedWindowBuilder> {

	WindowContainerManaged getContainerManaged();
	WindowContainerManagedWindowBuilderGetter setContainerManaged(WindowContainerManaged containerManaged);
	
	SystemAction getSystemAction();
	WindowContainerManagedWindowBuilderGetter setSystemAction(SystemAction systemAction);
	
}
