package org.cyk.utility.security.keycloak.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Policy implements Serializable {

	private String identifier;
	private String name;
	private Collection<Role> roles;
	
	public Boolean hasRoleName(String name) {
		if(StringHelper.isBlank(name) || CollectionHelper.isEmpty(roles))
			return null;
		return !roles.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList()).isEmpty();
	}
	
	@Override
	public String toString() {
		return identifier+"/"+name+"/"+roles;
	}
	
	/**/
	
	public static class RoleJson {
		String id;
	}
}