package org.cyk.utility.__kernel__.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

public interface UserBuilder {

	User build(Principal principal);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements UserBuilder,Serializable {
		
		@Override
		public User build(Principal principal) {
			if(principal instanceof KeycloakPrincipal)
				return __buildKeycloak__(principal);
			return null;
		}
		
		/**/
		
		protected User __buildKeycloak__(Principal principal) {
			if(!(principal instanceof KeycloakPrincipal))
				return null;
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
	
	static UserBuilder getInstance() {
		return Helper.getInstance(UserBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}