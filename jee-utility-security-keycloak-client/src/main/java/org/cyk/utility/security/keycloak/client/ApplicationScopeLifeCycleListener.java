package org.cyk.utility.security.keycloak.client;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.security.UserBuilder;
import org.cyk.utility.__kernel__.session.SessionManager;
import org.cyk.utility.security.keycloak.annotation.Keycloak;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		DependencyInjection.setQualifierClassTo(Keycloak.class,SessionManager.class,UserBuilder.class);
		LogHelper.logInfo("Keycloak security client initialized", getClass());
	}

	@Override
	public void __destroy__(Object object) {
		
	}
}