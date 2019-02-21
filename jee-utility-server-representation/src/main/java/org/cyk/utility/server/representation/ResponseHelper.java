package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.cyk.utility.helper.Helper;

public interface ResponseHelper extends Helper {

	Family getFamily(Response response);
	
	Boolean isFamily(Response response,Family family);
	Boolean isFamilySuccessful(Response response);
	Boolean isFamilyRedirection(Response response);
	Boolean isFamilyClientError(Response response);
	Boolean isFamilyServerError(Response response);
	
	Status getStatus(Response response);
	
	Boolean isStatus(Response response,Status status);
	
	//Successful
	Boolean isStatusSuccessfulOk(Response response);
	
	//Client Error
	Boolean isStatusClientErrorNotFound(Response response);
	
}
