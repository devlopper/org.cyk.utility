package org.cyk.utility.security.keycloak.server;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Role implements Serializable {

	private String identifier;
	private String name;
	
	public Role(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
