package org.cyk.utility.system;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface OperatingSystemCommandExecutor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	OperatingSystemCommandBuilder getCommand();
	OperatingSystemCommandBuilder getCommand(Boolean injectIfNull);
	OperatingSystemCommandExecutor setCommand(OperatingSystemCommandBuilder command);
	
	Boolean getIsWaitForTermination();
	OperatingSystemCommandExecutor setIsWaitForTermination(Boolean isWaitForTermination);
	
	Long getNumberOfMillisecondToWait();
	OperatingSystemCommandExecutor setNumberOfMillisecondToWait(Long numberOfMillisecondToWait);
	
	String getResult();
	OperatingSystemCommandExecutor setResult(String result);
	
	Process getProcess();
	OperatingSystemCommandExecutor setProcess(Process process);
}
