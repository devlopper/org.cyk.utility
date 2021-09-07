package org.cyk.utility.persistence.server.integration;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.persistence.server.Initializer;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener {

	public static Boolean INTEGRATION = Boolean.TRUE;
	
	@Override
	public void __initialize__(Object object) {
		if(Boolean.TRUE.equals(INTEGRATION))
			Initializer.initialize();
	}
	
	@Override
	public void __destroy__(Object object) {
		
	}
}