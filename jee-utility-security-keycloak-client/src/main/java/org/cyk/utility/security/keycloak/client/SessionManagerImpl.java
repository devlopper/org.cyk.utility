package org.cyk.utility.security.keycloak.client;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.session.SessionManager;
import org.cyk.utility.security.keycloak.KeycloakHelper;
import org.cyk.utility.security.keycloak.annotation.Keycloak;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;

@Keycloak
public class SessionManagerImpl extends SessionManager.AbstractImpl implements Serializable {

	@Override
	protected Boolean __isUserLogged__(HttpServletRequest request) {
		return request.getAttribute(KeycloakSecurityContext.class.getName()) instanceof RefreshableKeycloakSecurityContext;
	}
	
	@Override
	public String getUserName(Principal principal) {
		return KeycloakHelper.getUserName(principal);
	}
	
	@Override
	protected Boolean __isUserHasRole__(String role,HttpServletRequest request) {
		return KeycloakHelper.isUserHasRole(role, request);
	}
	
	@Override
	protected void __destroy__(String username, HttpServletRequest request) {
		super.__destroy__(username, request);
		if (request.getAttribute(KeycloakSecurityContext.class.getName()) instanceof RefreshableKeycloakSecurityContext) {
			RefreshableKeycloakSecurityContext keycloakSecurityContext = (RefreshableKeycloakSecurityContext)request.getAttribute(KeycloakSecurityContext.class.getName());
			keycloakSecurityContext.logout(keycloakSecurityContext.getDeployment());
			request.removeAttribute(KeycloakSecurityContext.class.getName());
			LogHelper.logInfo(String.format("Keycloak Security Context of <<%s>> has been logged out and removed",username), SessionHelper.class);
		}
	}
}
