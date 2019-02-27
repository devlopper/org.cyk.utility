package org.cyk.utility.client.controller.data;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.system.action.SystemAction;

public interface DataGetter extends FunctionWithPropertiesAsInput<Data> {

	SystemAction getSystemAction();
	DataGetter setSystemAction(SystemAction systemAction);
	
}
