package org.cyk.utility.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.time.TimeHelper;

@Dependent
public class OperatingSystemCommandExecutorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements OperatingSystemCommandExecutor,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Process process;
	private String result;
	private Long numberOfMillisecondToWait;
	private Boolean isWaitForTermination;
	private OperatingSystemCommandBuilder command;
	
	@Override
	protected void ____execute____() throws Exception {
		OperatingSystemCommandBuilder command = __injectValueHelper__().returnOrThrowIfBlank("operating system command builder", getCommand());
		String commandAsString = command.execute().getOutput();
		
		Process process = Runtime.getRuntime().exec(commandAsString);
		setProcess(process);
		
		Boolean isWaitForTermination = __injectValueHelper__().defaultToIfNull(getIsWaitForTermination(),Boolean.FALSE);
		
		if(Boolean.TRUE.equals(isWaitForTermination))
			process.waitFor();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        Collection<String> lines = new ArrayList<>();
        while ((line = input.readLine()) != null)
        	lines.add(line);
        
        setResult(StringHelper.concatenate(lines,ConstantSeparator.LINE_WITH_LINE_FEED));
        
		Long numberOfMillisecondToWait = getNumberOfMillisecondToWait();
		if(numberOfMillisecondToWait!=null && numberOfMillisecondToWait>0)
			__inject__(TimeHelper.class).pause(numberOfMillisecondToWait);
	}
	
	public static final String CMD_C_FORMAT = "cmd /c %s";
	
	@Override
	public Process getProcess() {
		return process;
	}
	
	@Override
	public OperatingSystemCommandExecutor setProcess(Process process) {
		this.process = process;
		return this;
	}
	
	@Override
	public OperatingSystemCommandBuilder getCommand() {
		return command;
	}
	
	@Override
	public OperatingSystemCommandBuilder getCommand(Boolean injectIfNull) {
		return (OperatingSystemCommandBuilder) __getInjectIfNull__(FIELD_COMMAND, injectIfNull);
	}
	
	@Override
	public OperatingSystemCommandExecutor setCommand(OperatingSystemCommandBuilder command) {
		this.command = command;
		return this;
	}

	@Override
	public Long getNumberOfMillisecondToWait() {
		return numberOfMillisecondToWait;
	}

	@Override
	public OperatingSystemCommandExecutor setNumberOfMillisecondToWait(Long numberOfMillisecondToWait) {
		this.numberOfMillisecondToWait = numberOfMillisecondToWait;
		return this;
	}
	
	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public OperatingSystemCommandExecutor setResult(String result) {
		this.result = result;
		return this;
	}

	@Override
	public Boolean getIsWaitForTermination() {
		return isWaitForTermination;
	}

	@Override
	public OperatingSystemCommandExecutor setIsWaitForTermination(Boolean isWaitForTermination) {
		this.isWaitForTermination = isWaitForTermination;
		return this;
	}

	/**/
	
	private static final String FIELD_COMMAND = "command";
}
