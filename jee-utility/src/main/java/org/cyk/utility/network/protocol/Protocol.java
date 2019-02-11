package org.cyk.utility.network.protocol;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.security.Credentials;

public interface Protocol extends Objectable {

	String getHost();
	Protocol setHost(String host);
	
	Integer getPort();
	Protocol setPort(Integer port);
	
	Boolean getIsAuthenticationRequired();
	Protocol setIsAuthenticationRequired(Boolean isAuthenticationRequired);
	
	Credentials getAuthenticationCredentials();
	Protocol setAuthenticationCredentials(Credentials authenticationCredentials);
	
	Boolean getIsSecuredConnectionRequired();
	Protocol setIsSecuredConnectionRequired(Boolean isSecuredConnectionRequired);
	
}
