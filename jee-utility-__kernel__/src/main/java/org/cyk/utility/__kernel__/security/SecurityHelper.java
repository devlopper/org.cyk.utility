package org.cyk.utility.__kernel__.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.identifier.resource.RequestGetter;
import org.cyk.utility.__kernel__.value.Value;

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
		if(PRINCIPALABLE.get() == null || Boolean.TRUE.equals(PRINCIPALABLE.get()))
			return getPrincipal(RequestGetter.getInstance().get());
		return null;
	}
	
	Value PRINCIPALABLE = new Value().set(Boolean.TRUE);
}