package org.cyk.utility.__kernel__.security;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String firstName;
	private String lastNames;
	private String names;
	
	public User(Principal principal) {
		if(principal instanceof KeycloakPrincipal) {
			KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
			AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
			name = accessToken.getPreferredUsername();
			firstName = accessToken.getGivenName();
			lastNames = accessToken.getFamilyName();
		}
		
		names = name;
		String firstNameLastNames = firstName;
		if(StringHelper.isBlank(firstNameLastNames))
			firstNameLastNames = lastNames;
		else
			firstNameLastNames = firstNameLastNames + " " + lastNames;
		if(StringHelper.isNotBlank(firstNameLastNames))
			names = firstNameLastNames;
	}
}
