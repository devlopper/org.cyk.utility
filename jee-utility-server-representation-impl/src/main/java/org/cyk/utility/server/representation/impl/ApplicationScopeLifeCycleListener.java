package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.node.SystemServer;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__initializeApplication__(object);
	}
	
	protected void __initializeApplication__(Object object) {
		SystemServer systemServer = __inject__(SystemServer.class);
		if(__inject__(StringHelper.class).isBlank(systemServer.getName()))
			systemServer.setName("SERVER_APP_NAME");
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
