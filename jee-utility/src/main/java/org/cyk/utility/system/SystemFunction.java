package org.cyk.utility.system;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.system.action.SystemAction;

public interface SystemFunction extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	SystemAction getAction();
	SystemFunction setAction(SystemAction action);
	
}
