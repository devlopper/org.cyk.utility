package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.method.AbstractMethodCallListenerImpl;

public abstract class AbstractCommandFunctionExecuteListenerImpl extends AbstractMethodCallListenerImpl implements CommandFunctionExecuteListener,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public CommandFunctionExecuteListener setObject(Object object) {
		return (CommandFunctionExecuteListener) super.setObject(object);
	}
	
}
