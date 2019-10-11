package org.cyk.utility.__kernel__.security.keycloak;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.admin.client.Keycloak;

public interface KeycloakHelper {

	static Keycloak getClient() {
		return null;
	}
	
	List<Object> CONSTANTS = new ArrayList<>(1);
	Integer CONSTANT_INSTANCE_GETTER_INDEX = 0;
}
