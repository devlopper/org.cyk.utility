package org.cyk.utility.system;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.time.TimeHelper;

public class OperatingSystemCommandExecutorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements OperatingSystemCommandExecutor,Serializable {
	private static final long serialVersionUID = 1L;

	private String command;
	private Long numberOfMillisecondToWait;
	
	@Override
	protected void ____execute____() throws Exception {
		String command = __injectValueHelper__().returnOrThrowIfBlank("operating system command", getCommand());
		Runtime.getRuntime().exec(command);
		Long numberOfMillisecondToWait = getNumberOfMillisecondToWait();
		if(numberOfMillisecondToWait!=null && numberOfMillisecondToWait>0)
			__inject__(TimeHelper.class).pause(numberOfMillisecondToWait);
	}
	
	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public OperatingSystemCommandExecutor setCommand(String command) {
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

}
