package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import org.cyk.utility.__kernel__.system.action.SystemAction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractSystemExceptionImpl extends RuntimeException implements SystemException,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	public AbstractSystemExceptionImpl(String message,Throwable throwable) {
		super(message,throwable);
	}
	
	public AbstractSystemExceptionImpl(String message) {
		super(message);
	}
	
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
