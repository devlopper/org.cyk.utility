package org.cyk.utility.system;

import org.cyk.utility.string.StringFunction;

public interface OperatingSystemCommandBuilder extends StringFunction {

	String getCommand();
	OperatingSystemCommandBuilder setCommand(String command);
	
	Boolean getIsTerminalStartable();
	OperatingSystemCommandBuilder setIsTerminalStartable(Boolean isTerminalStartable);
	
	String getTerminalTitle();
	OperatingSystemCommandBuilder setTerminalTitle(String terminalTitle);
	
	Boolean getIsTerminalShowable();
	OperatingSystemCommandBuilder setIsTerminalShowable(Boolean isTerminalShowable);
	
	Boolean getIsStartedTerminalClosable();
	OperatingSystemCommandBuilder setIsStartedTerminalClosable(Boolean isStartedTerminalClosable);
	
	String getWorkingDirectory();
	OperatingSystemCommandBuilder setWorkingDirectory(String workingDirectory);
	
	/**/
	
	String FORMAT_WINDOWS = "cmd /c %s";
}
