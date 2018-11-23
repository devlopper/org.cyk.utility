package org.cyk.utility.client.controller.data;

import org.cyk.utility.clazz.ClassFunction;
import org.cyk.utility.system.action.SystemAction;

public interface RowClassGetter extends ClassFunction {

	SystemAction getSystemAction();
	RowClassGetter setSystemAction(SystemAction systemAction);

}
