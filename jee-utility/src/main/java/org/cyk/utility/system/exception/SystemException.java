package org.cyk.utility.system.exception;

import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface SystemException {

	SystemAction getSystemAction();
	SystemException setSystemAction(SystemAction systemAction);
	
}
