package org.cyk.utility.system.exception;

import javax.ws.rs.core.Response;

public interface SystemClientException extends SystemException {

	Response getResponse();
	SystemClientException setResponse(Response response);
	
}
