package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class ResponseHelperImpl extends AbstractHelper implements ResponseHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Family getFamily(Response response) {
		return response == null ? null : Family.familyOf(response.getStatus());
	}
	
	@Override
	public Boolean isFamily(Response response, Family family) {
		return response == null || family == null ? null : family.equals(getFamily(response));
	}
	
	@Override
	public Boolean isFamilySuccessful(Response response) {
		return isFamily(response, Family.SUCCESSFUL);
	}

	@Override
	public Boolean isFamilyRedirection(Response response) {
		return isFamily(response, Family.REDIRECTION);
	}
	
	@Override
	public Boolean isFamilyClientError(Response response) {
		return isFamily(response, Family.CLIENT_ERROR);
	}

	@Override
	public Boolean isFamilyServerError(Response response) {
		return isFamily(response, Family.SERVER_ERROR);
	}

	@Override
	public Status getStatus(Response response) {
		return response == null ? null : Status.fromStatusCode(response.getStatus());
	}
	
	@Override
	public Boolean isStatus(Response response, Status status) {
		return response == null || status == null ? null : status.equals(getStatus(response));
	}
	
	@Override
	public Boolean isStatusSuccessfulOk(Response response) {
		return isStatus(response, Response.Status.OK);
	}
	
	@Override
	public Boolean isStatusClientErrorNotFound(Response response) {
		return isStatus(response, Response.Status.NOT_FOUND);
	}
}
