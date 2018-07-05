package org.cyk.utility.architecture.system;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface SystemFunction extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	SystemAction getAction();
	SystemFunction setAction(SystemAction action);
	
}
