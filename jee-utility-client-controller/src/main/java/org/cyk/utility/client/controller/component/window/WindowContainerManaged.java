package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.session.SessionUser;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface WindowContainerManaged extends Objectable {

	SystemAction getSystemAction();
	WindowContainerManaged setSystemAction(SystemAction systemAction);
	
	Window getWindow();
	WindowContainerManaged setWindow(Window window);
	
	String getContextDependencyInjectionBeanName();
	WindowContainerManaged setContextDependencyInjectionBeanName(String contextDependencyInjectionBeanName);

	@Deprecated
	SessionUser getSessionUser();
	
	@Deprecated
	WindowContainerManaged setSessionUser(SessionUser sessionUser);
}
