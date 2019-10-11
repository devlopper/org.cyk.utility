package org.cyk.utility.__kernel__.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class LogMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String template;
	private List<Object> arguments;
	private String value;
	
	public List<Object> getArguments(Boolean injectIfNull) {
		if(arguments == null && Boolean.TRUE.equals(injectIfNull))
			arguments = new ArrayList<>();
		return arguments;
	}
	
	public LogMessage add(String string) {
		LogHelper.addStringToMessage(this, string);
		return this;
	}
	
	public LogMessage addArgument(String name,Object value) {
		LogHelper.addArgumentToMessage(this, name, value);
		return this;
	}
}
