package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.primefaces.PrimeFaces;

public abstract class AbstractCommandFunctionFunctionRunnableImpl extends org.cyk.utility.client.controller.component.command.AbstractCommandFunctionFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __act__(SystemAction action,Object data) {
		if(action instanceof SystemActionAdd) {
			PrimeFaces.current().dialog().closeDynamic(data);
		}else
			super.__act__(action,data);
	}
	
}
