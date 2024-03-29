package org.cyk.utility.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.StringFormat;

@Dependent
public class OperatingSystemCommandBuilderImpl extends AbstractStringFunctionImpl implements OperatingSystemCommandBuilder,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Boolean isTerminalStartable,isTerminalShowable,isStartedTerminalClosable;
	private String command,terminalTitle,workingDirectory;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getFormat(Boolean.TRUE).setValue(FORMAT_WINDOWS);
	}
	
	@Override
	protected StringFormat __getFormat__(StringFormat format) {
		String command = ValueHelper.returnOrThrowIfBlank("command", getCommand());
		Collection<String> options = new ArrayList<>();
		Boolean isTerminalStartable = ValueHelper.defaultToIfNull(getIsTerminalStartable(), Boolean.FALSE);
		if(Boolean.TRUE.equals(isTerminalStartable)) {
			String title = getTerminalTitle();
			if(StringHelper.isBlank(title))
				title = "Terminal for <<"+command+">>";
			options.add("start \""+title+"\"");
			
			Boolean isTerminalShowable = ValueHelper.defaultToIfNull(getIsTerminalShowable(), Boolean.FALSE);
			if(Boolean.FALSE.equals(isTerminalShowable))
				options.add("/b");
			
			Boolean isStartedTerminalClosable = ValueHelper.defaultToIfNull(getIsStartedTerminalClosable(), Boolean.FALSE);
			if(Boolean.TRUE.equals(isStartedTerminalClosable))
				options.add("cmd /c");
		}
		
		String workingDirectory = getWorkingDirectory();
		if(StringHelper.isNotBlank(workingDirectory))
			options.add("/d \""+workingDirectory+"\"");
		
		
		if(CollectionHelper.isNotEmpty(options))
			command = StringHelper.concatenate(options,ConstantCharacter.SPACE.toString()) + ConstantCharacter.SPACE + command;
		
		format.setArguments(0,command);
		return super.__getFormat__(format);
	}
	
	@Override
	public String getTerminalTitle() {
		return terminalTitle;
	}

	@Override
	public OperatingSystemCommandBuilder setTerminalTitle(String terminalTitle) {
		this.terminalTitle = terminalTitle;
		return this;
	}

	@Override
	public Boolean getIsTerminalShowable() {
		return isTerminalShowable;
	}

	@Override
	public OperatingSystemCommandBuilder setIsTerminalShowable(Boolean isTerminalStartable) {
		this.isTerminalShowable = isTerminalStartable;
		return this;
	}

	@Override
	public Boolean getIsStartedTerminalClosable() {
		return isStartedTerminalClosable;
	}

	@Override
	public OperatingSystemCommandBuilder setIsStartedTerminalClosable(Boolean isStartedTerminalClosable) {
		this.isStartedTerminalClosable = isStartedTerminalClosable;
		return this;
	}

	@Override
	public String getWorkingDirectory() {
		return workingDirectory;
	}

	@Override
	public OperatingSystemCommandBuilder setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
		return this;
	}
	
	@Override
	public Boolean getIsTerminalStartable() {
		return isTerminalStartable;
	}
	
	@Override
	public OperatingSystemCommandBuilder setIsTerminalStartable(Boolean isTerminalStartable) {
		this.isTerminalStartable = isTerminalStartable;
		return this;
	}
	
	@Override
	public String getCommand() {
		return command;
	}
	
	@Override
	public OperatingSystemCommandBuilder setCommand(String command) {
		this.command = command;
		return this;
	}

}
