package org.cyk.utility.client.controller.data;

import org.cyk.utility.string.StringsFunction;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface DataMethodsNamesGetter extends StringsFunction {

	SystemAction getSystemAction();
	DataMethodsNamesGetter setSystemAction(SystemAction systemAction);
	
}
