package org.cyk.utility.__kernel__.security;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uuid;
	private String name;
	private String firstName;
	private String lastNames;
	private String names;
	private Map<String,Object> attributes;
	/*
	public User(Principal principal) {
		if(principal instanceof KeycloakPrincipal) {
			KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
			AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
			name = accessToken.getPreferredUsername();
			firstName = accessToken.getGivenName();
			lastNames = accessToken.getFamilyName();
			if(MapHelper.isNotEmpty(accessToken.getOtherClaims())) {
				attributes = new HashMap<String, Object>(accessToken.getOtherClaims());
				uuid = (String) getAttributeValue(ATTRIBUTE_NAME_UUID);
			}
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
	*/
	public Object getAttributeValue(String key) {
		if(MapHelper.isEmpty(attributes))
			return null;
		return attributes.get(key);
	}
	
	/**/
	
	public static final String ATTRIBUTE_NAME_UUID = "uuid";
}