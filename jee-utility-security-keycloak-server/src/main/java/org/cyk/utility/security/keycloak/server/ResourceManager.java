package org.cyk.utility.security.keycloak.server;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface ResourceManager {

	/**/
	
	static ResourceManager getInstance() {
		return Helper.getInstance(ResourceManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}