package org.cyk.utility.client.controller.session;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class SessionUserImpl extends AbstractObject implements SessionUser,Serializable {
	private static final long serialVersionUID = 1L;

	private Principal principal;
	
	@Override
	public Principal getPrincipal() {
		return principal;
	}
	
	@Override
	public SessionUser setPrincipal(Principal principal) {
		this.principal = principal;
		return this;
	}
	
	@Override
	public SessionUser setIdentifier(Object identifier) {
		return (SessionUser) super.setIdentifier(identifier);
	}
}
