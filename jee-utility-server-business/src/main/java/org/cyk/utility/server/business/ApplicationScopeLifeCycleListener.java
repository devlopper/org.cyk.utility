package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		initialize();
	}
	
	public static void initialize() {
		org.cyk.utility.server.persistence.ApplicationScopeLifeCycleListener.initialize();
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}