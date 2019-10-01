package org.cyk.utility.__kernel__.identifier.resource;

import org.cyk.utility.__kernel__.AbstractAsFunctionParameterIdentified;
import org.cyk.utility.__kernel__.system.action.SystemActionAsFunctionParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class PathAsFunctionParameter extends AbstractAsFunctionParameterIdentified<String,String> {
	
	private SystemActionAsFunctionParameter systemAction;
	
	public SystemActionAsFunctionParameter getSystemAction(Boolean injectIfNull) {
		if(systemAction == null && Boolean.TRUE.equals(injectIfNull))
			setSystemAction(systemAction = new SystemActionAsFunctionParameter());
		return systemAction;
	}
	
}
