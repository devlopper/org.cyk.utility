package org.cyk.utility.__kernel__.security.keycloak;

import java.security.Principal;
import java.util.List;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessToken;

public interface KeycloakHelper {

	static Keycloak getClient(Boolean throwIfNull) {
		Keycloak client =  KeycloakClientGetter.getInstance().get();
		if(client == null && Boolean.TRUE.equals(throwIfNull))
			throw new RuntimeException("keycloak client is null");
		return client;
	}
	
	static Keycloak getClient() {
		return getClient(Boolean.TRUE);
	}
	
	static RealmResource getRealmResource() {
		return getClient().realm(ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_REALM_NAME));
	}
	
	static AccessToken getAccessToken(Principal principal) {
		if(principal == null)
			return null;
		if(principal instanceof KeycloakPrincipal)
			return ((KeycloakPrincipal<?>) principal).getKeycloakSecurityContext().getToken();
		throw new RuntimeException("cannot get access token from principal type "+principal.getClass());
	}
	
	static String getUserName(Principal principal) {
		if(principal == null)
			return null;
		AccessToken accessToken = getAccessToken(principal);
		if(accessToken == null)
			return principal.getName();
		return accessToken.getPreferredUsername();
	}
	
	static void executeActionEmailUpdatePassword(String identifier) {
		if(StringHelper.isBlank(identifier))
			return;
		RealmResource realmResource = getRealmResource();
		UserResource userResource;
		try {
			userResource = realmResource.users().get(identifier);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		if(userResource == null)
			return;
		userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
	}
	
	static void executeActionEmailUpdatePassword(Principal principal) {
		if(principal == null)
			return;
		executeActionEmailUpdatePassword(principal.getName());
	}
	
	
}
