package org.cyk.utility.request;

import java.security.Principal;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface RequestPrincipalGetter extends FunctionWithPropertiesAsInput<Principal> {
	
	Object getRequest();
	RequestPrincipalGetter setRequest(Object request);
	
}
