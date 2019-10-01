package org.cyk.utility.__kernel__.identifier.resource;

import org.cyk.utility.__kernel__.AbstractAsFunctionParameter;
import org.cyk.utility.__kernel__.system.action.SystemActionAsFunctionParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class QueryAsFunctionParameter extends AbstractAsFunctionParameter<String> {
	
	private SystemActionAsFunctionParameter systemAction;
	
	public SystemActionAsFunctionParameter getSystemAction(Boolean injectIfNull) {
		if(systemAction == null && Boolean.TRUE.equals(injectIfNull))
			systemAction = new SystemActionAsFunctionParameter();
		return systemAction;
	}
	
}
