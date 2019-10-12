package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.__kernel__.session.Session;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.Objectable;

public interface WindowContainerManaged extends Objectable {

	SystemAction getSystemAction();
	WindowContainerManaged setSystemAction(SystemAction systemAction);
	
	Window getWindow();
	WindowContainerManaged setWindow(Window window);
	
	String getContextDependencyInjectionBeanName();
	WindowContainerManaged setContextDependencyInjectionBeanName(String contextDependencyInjectionBeanName);
	
	Session getSession();
	WindowContainerManaged setSession(Session session);
}
