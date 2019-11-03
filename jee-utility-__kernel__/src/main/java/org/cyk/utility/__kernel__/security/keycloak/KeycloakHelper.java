package org.cyk.utility.__kernel__.security.keycloak;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;

public interface KeycloakHelper {

	static Keycloak getClient() {
		return null;
	}
	
	static String getUserName(Principal principal) {
		if(principal instanceof KeycloakPrincipal) {
			KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
			AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
			return accessToken.getPreferredUsername();
		}
		return principal.getName();
	}
	
	List<Object> CONSTANTS = new ArrayList<>(1);
	Integer CONSTANT_INSTANCE_GETTER_INDEX = 0;
}
