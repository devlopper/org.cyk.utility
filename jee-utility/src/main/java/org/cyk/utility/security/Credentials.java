package org.cyk.utility.security;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Credentials extends Objectable {

	@Override Credentials setIdentifier(java.lang.Object identifier);
	
	Object getSecret();
	Credentials setSecret(Object secret);

}
