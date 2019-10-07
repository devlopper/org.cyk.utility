package org.cyk.utility.__kernel__.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.DependencyInjection;

public interface SecurityHelper {

	static Principal getPrincipalFromRequest(Object request) {
		if(request == null)
			return null;
		if(request instanceof HttpServletRequest)
			return ((HttpServletRequest)request).getUserPrincipal();
		throw new RuntimeException("get principal from request type "+request.getClass()+" not yet implemented");
	}
	
	static Principal getPrincipal(Object request) {
		if(request == null)
			return null;
		return getPrincipalFromRequest(request);
	}
	
	static Principal getPrincipal() {
		return getPrincipal(DependencyInjection.inject(HttpServletRequest.class));
	}
}
