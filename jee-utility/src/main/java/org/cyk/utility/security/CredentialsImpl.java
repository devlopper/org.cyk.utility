package org.cyk.utility.security;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class CredentialsImpl extends AbstractObject implements Credentials,Serializable {
	private static final long serialVersionUID = 1L;

	private Object secret;
	
	@Override
	public Object getSecret() {
		return secret;
	}

	@Override
	public Credentials setSecret(Object secret) {
		this.secret = secret;
		return this;
	}
	
	@Override
	public Credentials setIdentifier(Object identifier) {
		return (Credentials) super.setIdentifier(identifier);
	}

}
