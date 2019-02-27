package org.cyk.utility.client.controller.data;

import org.cyk.utility.string.StringsFunction;
import org.cyk.utility.system.action.SystemAction;

public interface DataFieldsNamesGetter extends StringsFunction {

	SystemAction getSystemAction();
	DataFieldsNamesGetter setSystemAction(SystemAction systemAction);
	
}
