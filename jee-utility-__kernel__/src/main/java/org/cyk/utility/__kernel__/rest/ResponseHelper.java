package org.cyk.utility.__kernel__.rest;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface ResponseHelper {

	static Family getFamily(Response response) {
		return response == null ? null : Family.familyOf(response.getStatus());
	}
	
	static Boolean isFamily(Response response, Family family) {
		return response == null || family == null ? null : family.equals(getFamily(response));
	}
	
	static Boolean isFamilySuccessful(Response response) {
		return isFamily(response, Family.SUCCESSFUL);
	}

	static Boolean isFamilyRedirection(Response response) {
		return isFamily(response, Family.REDIRECTION);
	}
	
	static Boolean isFamilyClientError(Response response) {
		return isFamily(response, Family.CLIENT_ERROR);
	}

	static Boolean isFamilyServerError(Response response) {
		return isFamily(response, Family.SERVER_ERROR);
	}

	static Status getStatus(Response response) {
		return response == null ? null : Status.fromStatusCode(response.getStatus());
	}
	
	static Boolean isStatus(Response response, Status status) {
		return response == null || status == null ? null : status.equals(getStatus(response));
	}
	
	static Boolean isStatusSuccessfulOk(Response response) {
		return isStatus(response, Response.Status.OK);
	}
	
	static Boolean isStatusClientErrorNotFound(Response response) {
		return isStatus(response, Response.Status.NOT_FOUND);
	}
	
	static String join(Collection<String> strings) {
		return String.join(HEADER_VALUES_SEPARATOR, strings);
	}
	
	static Collection<String> disjoin(String value) {
		return value == null || value.isBlank() ? null :  List.of(value.split(HEADER_VALUES_SEPARATOR));
	}
	
	static void addHeader(ResponseBuilder response, String name, Object value) {
		if(response != null && !name.isBlank() && value != null)
			response.header(name, value);
	}
	
	static void addHeader(ResponseBuilder response, String name, Collection<String> strings) {
		addHeader(response,name,join(strings));
	}
	
	static String getHeader(Response response, String name) {
		if(response == null || name == null || name.isBlank())
			return null;
		return response.getHeaderString(name);
	}
	
	static Collection<String> getHeaderAndDisjoin(Response response, String name) {
		if(response == null || name == null || name.isBlank())
			return null;
		String value = getHeader(response, name);
		if(StringHelper.isBlank(value))
			return null;
		return disjoin(value);
	}
	
	static Long getHeaderXTotalCount(Response response) {
		return NumberHelper.getLong(getHeader(response, HEADER_X_TOTAL_COUNT), null);
	}
	
	static Collection<String> getHeaderEntitiesSystemIdentifiers(Response response) {
		return getHeaderAndDisjoin(response, HEADER_ENTITY_IDENTIFIER_SYSTEM);
	}
	
	static Collection<String> getHeaderEntitiesBusinessIdentifiers(Response response) {
		return getHeaderAndDisjoin(response, HEADER_ENTITY_IDENTIFIER_BUSINESS);
	}
	
	/**/
	
	String HEADER_VALUES_SEPARATOR = ConstantCharacter.COMA.toString();
	String HEADER_ENTITY_IDENTIFIER_SYSTEM = "entity-identifier-system";
	String HEADER_ENTITY_IDENTIFIER_BUSINESS = "entity-identifier-business";	
	String HEADER_COLLECTION_SIZE = "collection-size";
	String HEADER_X_TOTAL_COUNT = "X-Total-Count";
	String HEADER_PROCESSING_START_TIME = "processing-start-time";
	String HEADER_PROCESSING_END_TIME = "processing-end-time";
	String HEADER_PROCESSING_DURATION = "processing-duration";
}
