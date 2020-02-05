package org.cyk.utility.__kernel__.protocol;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.security.Credentials;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractProperties extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String host,proxyHost;
	private Integer port,proxyPort;
	private Boolean isAuthenticationRequired,isSecuredConnectionRequired;
	private Credentials authenticationCredentials,proxyAuthenticationCredentials;
	
}
