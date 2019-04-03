package org.cyk.utility.system;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface OperatingSystemCommandExecutor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	String getCommand();
	OperatingSystemCommandExecutor setCommand(String command);
	
	Long getNumberOfMillisecondToWait();
	OperatingSystemCommandExecutor setNumberOfMillisecondToWait(Long numberOfMillisecondToWait);
}
