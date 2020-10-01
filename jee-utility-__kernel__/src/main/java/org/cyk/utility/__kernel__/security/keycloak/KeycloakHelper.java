package org.cyk.utility.__kernel__.security.keycloak;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

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
	
	static Collection<Role> getRoles() {
		RolesResource rolesResource = getRealmResource(Boolean.TRUE).roles();
		if(rolesResource == null)
			throw new RuntimeException("keycloak roles resource not found");
		Collection<RoleRepresentation> roleRepresentations = rolesResource.list();
		if(CollectionHelper.isEmpty(roleRepresentations))
			return null;
		return roleRepresentations.stream().map(roleRepresentation -> new Role().setIdentifier(roleRepresentation.getId()).setName(roleRepresentation.getName())).collect(Collectors.toList());
	}
	
	static Collection<User> getUsers() {
		UsersResource usersResource = getRealmResource(Boolean.TRUE).users();
		if(usersResource == null)
			throw new RuntimeException("keycloak users resource not found");
		Collection<UserRepresentation> userRepresentations = usersResource.list();
		if(CollectionHelper.isEmpty(userRepresentations))
			return null;
		return userRepresentations.stream().map(userRepresentation -> new User().setIdentifier(userRepresentation.getId()).setName(userRepresentation.getUsername())).collect(Collectors.toList());
	}
}