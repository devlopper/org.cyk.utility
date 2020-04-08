package org.cyk.utility.__kernel__.security.keycloak;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Role {

	private String identifier;
	private String name;
	
}
