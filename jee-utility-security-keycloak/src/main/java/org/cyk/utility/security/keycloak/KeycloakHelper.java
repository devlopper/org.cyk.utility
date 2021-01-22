package org.cyk.utility.security.keycloak;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessToken;

public interface KeycloakHelper {

	static Keycloak getClient(Boolean throwIfNull) {
		Keycloak client =  KeycloakClientGetter.getInstance().get();
		if(client == null && Boolean.TRUE.equals(throwIfNull))
			throw new RuntimeException("keycloak client is required");
		return client;
	}
	
	static Keycloak getClient() {
		return getClient(Boolean.TRUE);
	}
	
	static RealmResource getRealmResource(Boolean throwIfNull) {
		return getClient(throwIfNull).realm(ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_REALM_NAME));
	}
	
	static RealmResource getRealmResource() {
		return getRealmResource(Boolean.TRUE);
	}
	
	static RolesResource getRolesResource() {
		return getRealmResource(Boolean.TRUE).roles();
	}
	
	static UsersResource getUsersResource() {
		return getRealmResource(Boolean.TRUE).users();
	}
	
	static ClientsResource getClientsResource() {
		return getRealmResource(Boolean.TRUE).clients();
	}
	
	static AccessToken getAccessToken(Principal principal) {
		if(principal == null)
			return null;
		if(principal instanceof KeycloakPrincipal)
			return ((KeycloakPrincipal<?>) principal).getKeycloakSecurityContext().getToken();
		throw new RuntimeException("cannot get access token from principal type "+principal.getClass());
	}
	
	static AccessToken getAccessToken(HttpServletRequest request) {
		if(request == null)
			return null;
		if (request.getAttribute(KeycloakSecurityContext.class.getName()) instanceof RefreshableKeycloakSecurityContext)
			return ((RefreshableKeycloakSecurityContext)request.getAttribute(KeycloakSecurityContext.class.getName())).getToken();
		return null;
	}
	
	static Boolean isUserHasRole(String role,HttpServletRequest request) {
		if(request == null)
			return null;
		AccessToken accessToken = getAccessToken(request);
		if(accessToken == null || accessToken.getRealmAccess() == null)
			return null;
		return CollectionHelper.contains(accessToken.getRealmAccess().getRoles(), role);
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
	
	static void executeActionEmailUpdatePassword() {
		executeActionEmailUpdatePassword(SecurityHelper.getPrincipal());
	}
}