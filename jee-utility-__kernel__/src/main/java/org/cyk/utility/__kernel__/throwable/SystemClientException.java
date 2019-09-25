package org.cyk.utility.__kernel__.throwable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface SystemClientException extends SystemException {

	Response getResponse();
	SystemClientException setResponse(Response response);

	@Override SystemClientException setSystemAction(SystemAction systemAction);
	
}
