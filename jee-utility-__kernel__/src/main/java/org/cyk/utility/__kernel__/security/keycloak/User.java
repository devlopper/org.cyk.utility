package org.cyk.utility.__kernel__.security.keycloak;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class User implements Serializable {

	private String identifier;
	private String name;
	private String firstName;
	private String lastNames;
	private String civility;
	private String electronicMailAddress;
	private String mobilePhoneNumber;
	private String pass;
	private Collection<Role> roles;
	private Map<String,List<String>> attributes;
	
	public Collection<Role> getRoles(Boolean injectIfNull) {
		if(roles == null && Boolean.TRUE.equals(injectIfNull))
			roles = new LinkedHashSet<>();
		return roles;
	}
	
	public User addRoles(Collection<Role> roles) {
		if(CollectionHelper.isEmpty(roles))
			return this;
		getRoles(Boolean.TRUE).addAll(roles);
		return this;
	}
	
	public User addRoles(Role...roles) {
		if(ArrayHelper.isEmpty(roles))
			return this;
		return addRoles(CollectionHelper.listOf(roles));
	}
	
	@Override
	public String toString() {
		return name+" : "+roles;
	}
}