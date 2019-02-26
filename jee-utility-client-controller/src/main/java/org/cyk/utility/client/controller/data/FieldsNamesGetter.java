package org.cyk.utility.client.controller.data;

import org.cyk.utility.string.StringsFunction;
import org.cyk.utility.system.action.SystemAction;

public interface FieldsNamesGetter extends StringsFunction {

	SystemAction getSystemAction();
	FieldsNamesGetter setSystemAction(SystemAction systemAction);
	
}
