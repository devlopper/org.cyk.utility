package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.session.SessionUser;

public interface WindowContainerManaged extends Objectable {

	Window getWindow();
	WindowContainerManaged setWindow(Window window);
	
	String getContextDependencyInjectionBeanName();
	WindowContainerManaged setContextDependencyInjectionBeanName(String contextDependencyInjectionBeanName);

	SessionUser getSessionUser();
	WindowContainerManaged setSessionUser(SessionUser sessionUser);
}
