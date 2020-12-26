package org.cyk.utility.security.keycloak.client;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.security.UserBuilder;
import org.cyk.utility.__kernel__.session.SessionManager;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.security.keycloak.annotation.Keycloak;
import org.keycloak.adapters.servlet.KeycloakOIDCFilter;

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
	
	/**/
	
	public static void enable(ServletContext context,Collection<String> urlsPatterns) {
		if(Boolean.TRUE.equals(ConfigurationHelper.getValueAsBoolean(VariableName.KEYCLOAK_ENABLED))) {
			FilterRegistration filterRegistration = context.addFilter("Keycloak Filter", KeycloakOIDCFilter.class);
			filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD)
					, Boolean.TRUE, urlsPatterns.toArray(new String[] {}));
			LogHelper.logInfo("Keycloak Filter has been registered", ApplicationScopeLifeCycleListener.class);
		}
	}
	
	public static void enable(ServletContext context,String...urlsPatterns) {
		enable(context, CollectionHelper.listOf(urlsPatterns));
	}
}