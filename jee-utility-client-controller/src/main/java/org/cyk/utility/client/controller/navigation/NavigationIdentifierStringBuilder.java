package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.string.StringFunction;
import org.cyk.utility.system.action.SystemAction;

public interface NavigationIdentifierStringBuilder extends StringFunction {

	SystemAction getSystemAction();
	NavigationIdentifierStringBuilder setSystemAction(SystemAction systemAction);
	
	/**/
	/**
	 * entity action View
	 */
	String FORMAT = "%s%sView";
	
	Integer FORMAT_ARGUMENT_ENTITY = 0;
	Integer FORMAT_ARGUMENT_ACTION = 1;
}
