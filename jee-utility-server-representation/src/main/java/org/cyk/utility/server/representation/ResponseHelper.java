package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
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

	/**/
	
	String join(Collection<String> strings);
	Collection<String> disjoin(String value);
	
	ResponseHelper addHeader(ResponseBuilder response,String name,Object value);
	ResponseHelper addHeader(ResponseBuilder response,String name,Collection<String> strings);
	
	String getHeader(Response response,String name);
	Collection<String> getHeaderAndDisjoin(Response response,String name);
	
	Long getHeaderXTotalCount(Response response);
}
