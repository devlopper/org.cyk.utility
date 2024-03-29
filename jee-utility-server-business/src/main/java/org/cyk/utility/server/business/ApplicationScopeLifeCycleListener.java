package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.utility.server.persistence.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}