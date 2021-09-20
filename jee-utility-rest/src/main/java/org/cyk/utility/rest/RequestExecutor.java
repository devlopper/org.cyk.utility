package org.cyk.utility.rest;

import javax.ws.rs.core.Response;

public interface RequestExecutor {

	Response execute(Request request);
	
	/**/
	
	public static interface Request {
		
		ResponseBuilder.Arguments execute();
		
	}
}