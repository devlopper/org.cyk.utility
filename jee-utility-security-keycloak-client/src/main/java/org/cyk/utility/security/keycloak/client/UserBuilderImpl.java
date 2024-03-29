package org.cyk.utility.security.keycloak.client;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.security.User;
import org.cyk.utility.__kernel__.security.UserBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.security.keycloak.annotation.Keycloak;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

@Keycloak
public class UserBuilderImpl extends UserBuilder.AbstractImpl implements Serializable {

	@Override
	public User build(Principal principal) {
		if(!(principal instanceof KeycloakPrincipal)) {
			LogHelper.logWarning("Principal is not from keycloak", getClass());
			return null;
		}
		KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
		AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
		User user = new User();
		user.setName(accessToken.getPreferredUsername());
		user.setFirstName(accessToken.getGivenName());
		user.setLastNames(accessToken.getFamilyName());
		if(MapHelper.isNotEmpty(accessToken.getOtherClaims())) {
			user.setAttributes(new HashMap<String, Object>(accessToken.getOtherClaims()));
			user.setUuid((String) user.getAttributeValue(User.ATTRIBUTE_NAME_UUID));
		}
		user.setNames(user.getName());
		String firstNameLastNames = user.getFirstName();
		if(StringHelper.isBlank(firstNameLastNames))
			firstNameLastNames = user.getLastNames();
		else
			firstNameLastNames = firstNameLastNames + " " + user.getLastNames();
		if(StringHelper.isNotBlank(firstNameLastNames))
			user.setNames(firstNameLastNames);	
		return user;
	}
	
}
