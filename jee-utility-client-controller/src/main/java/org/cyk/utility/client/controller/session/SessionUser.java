package org.cyk.utility.client.controller.session;

import java.security.Principal;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

@Deprecated
public interface SessionUser extends Objectable {

	Principal getPrincipal();
	SessionUser setPrincipal(Principal principal);
	
	@Override SessionUser setIdentifier(Object identifier);
}
