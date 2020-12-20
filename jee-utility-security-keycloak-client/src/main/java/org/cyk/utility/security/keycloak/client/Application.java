package org.cyk.utility.security.keycloak.client;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.security.UserBuilder;
import org.cyk.utility.__kernel__.session.SessionManager;
import org.cyk.utility.security.keycloak.annotation.Keycloak;

@Deprecated
public interface Application {

	static void initialize() {
		DependencyInjection.setQualifierClassTo(Keycloak.class,SessionManager.class,UserBuilder.class);
	}
	
}
