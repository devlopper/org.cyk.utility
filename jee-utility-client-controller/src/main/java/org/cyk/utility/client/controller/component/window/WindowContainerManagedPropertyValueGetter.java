package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface WindowContainerManagedPropertyValueGetter extends FunctionWithPropertiesAsInput<Object> {

	WindowContainerManagedPropertyValueGetter setContainerManaged(WindowContainerManaged containerManaged);
	WindowContainerManaged getContainerManaged();
	
	WindowContainerManagedPropertyValueGetter setProperty(WindowContainerManagedProperty property);
	WindowContainerManagedProperty getProperty();
	
	WindowContainerManagedPropertyValueGetter setContainer(WindowContainerManagedWindowBuilder container);
	WindowContainerManagedWindowBuilder getContainer();
	
}
