package org.cyk.utility.__kernel__.security.keycloak;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Permission implements Serializable {

	private String identifier;
	private String name,type;
	private Resource resource;
	private Collection<Policy> policies;
	
	public Collection<Policy> getPolicies(Boolean injectIfNull) {
		if(policies == null && Boolean.TRUE.equals(injectIfNull))
			policies = new ArrayList<>();
		return policies;
	}
	
	@Override
	public String toString() {
		return name+"/"+type;
	}
	
	/**/
	
	public static class RoleJson {
		String id;
	}
}