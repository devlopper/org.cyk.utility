package org.cyk.utility.playground.server;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.playground.server.persistence.entities.ApplicationScopeLifeCycleListenerEntities;
import org.cyk.utility.server.representation.DataLoader;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(ApplicationScopeLifeCycleListenerEntities.class).initialize(null);
		__setQualifierClassTo__(org.cyk.utility.playground.server.System.class, DataLoader.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
