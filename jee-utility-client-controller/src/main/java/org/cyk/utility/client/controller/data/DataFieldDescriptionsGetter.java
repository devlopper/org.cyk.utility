package org.cyk.utility.client.controller.data;

import org.cyk.utility.field.FieldDescriptions;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.system.action.SystemAction;

public interface DataFieldDescriptionsGetter extends FunctionWithPropertiesAsInput<FieldDescriptions> {

	SystemAction getSystemAction();
	DataFieldDescriptionsGetter setSystemAction(SystemAction systemAction);
	
}