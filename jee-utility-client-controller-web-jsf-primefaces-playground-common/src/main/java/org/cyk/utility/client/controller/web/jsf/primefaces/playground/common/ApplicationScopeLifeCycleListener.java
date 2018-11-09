package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.command.CommandFunctionExecuteListenerThroughImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(CommandFunctionExecuteListenerThroughImpl.class, CommandFunctionExecuteListenerThroughFunctionRunnableImpl.class);
	}
	
	@Override
	protected void __destroy__(Object object) {}
	
}
