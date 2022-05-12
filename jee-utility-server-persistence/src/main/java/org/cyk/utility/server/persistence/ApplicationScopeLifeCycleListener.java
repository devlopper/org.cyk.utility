package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		initialize();
	}
	
	public static void initialize() {
		org.cyk.utility.ApplicationScopeLifeCycleListener.initialize();
	}
	
	@Override
	public void __destroy__(Object object) {}
}
