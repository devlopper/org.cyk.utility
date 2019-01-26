package org.cyk.utility.system.exception;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemAction;

public class AbstractSystemExceptionImpl extends RuntimeException implements SystemException,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public SystemException setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
}
