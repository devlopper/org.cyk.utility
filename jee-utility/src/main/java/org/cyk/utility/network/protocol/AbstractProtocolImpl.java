package org.cyk.utility.network.protocol;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.security.Credentials;

public abstract class AbstractProtocolImpl extends AbstractObject implements Protocol,Serializable {
	private static final long serialVersionUID = 1L;

	private String host;
	private Integer port;
	private Boolean isAuthenticationRequired,isSecuredConnectionRequired;
	private Credentials authenticationCredentials;
	
	@Override
	public String getHost() {
		return host;
	}
	
	@Override
	public Protocol setHost(String host) {
		this.host = host;
		return this;
	}
	
	@Override
	public Integer getPort() {
		return port;
	}
	
	@Override
	public Protocol setPort(Integer port) {
		this.port = port;
		return this;
	}
	
	@Override
	public Boolean getIsAuthenticationRequired() {
		return isAuthenticationRequired;
	}
	
	@Override
	public Protocol setIsAuthenticationRequired(Boolean isAuthenticationRequired) {
		this.isAuthenticationRequired = isAuthenticationRequired;
		return this;
	}
	
	@Override
	public Boolean getIsSecuredConnectionRequired() {
		return isSecuredConnectionRequired;
	}
	
	@Override
	public Protocol setIsSecuredConnectionRequired(Boolean isSecuredConnectionRequired) {
		this.isSecuredConnectionRequired = isSecuredConnectionRequired;
		return this;
	}
	
	@Override
	public Credentials getAuthenticationCredentials() {
		return authenticationCredentials;
	}
	
	@Override
	public Protocol setAuthenticationCredentials(Credentials authenticationCredentials) {
		this.authenticationCredentials = authenticationCredentials;
		return this;
	}
}
