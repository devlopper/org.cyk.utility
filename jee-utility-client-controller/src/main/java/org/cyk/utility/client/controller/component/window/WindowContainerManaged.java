package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.Objectable;

public interface WindowContainerManaged extends Objectable {

	Window getWindow();
	WindowContainerManaged setWindow(Window window);
	
}
