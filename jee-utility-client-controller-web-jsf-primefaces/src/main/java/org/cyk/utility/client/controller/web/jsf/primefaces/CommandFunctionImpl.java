package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.primefaces.PrimeFaces;

@Primefaces
public class CommandFunctionImpl extends AbstractCommandFunctionImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __act__(SystemAction action,Object data) {
		if(action instanceof SystemActionAdd) {
			PrimeFaces.current().dialog().closeDynamic(data);
		}else
			super.__act__(action,data);
	}
	
}
