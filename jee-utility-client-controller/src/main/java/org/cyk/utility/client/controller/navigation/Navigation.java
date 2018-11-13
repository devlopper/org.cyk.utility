package org.cyk.utility.client.controller.navigation;

import java.net.URL;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public interface Navigation extends Objectable {

	SystemAction getSystemAction();
	Navigation setSystemAction(SystemAction systemAction);
	
	Strings getDynamicParameterNames();
	Navigation setDynamicParameterNames(Strings dynamicParameterNames);
	
	URL getUniformResourceLocator();
	Navigation setUniformResourceLocator(URL uniformResourceLocator);
	
}