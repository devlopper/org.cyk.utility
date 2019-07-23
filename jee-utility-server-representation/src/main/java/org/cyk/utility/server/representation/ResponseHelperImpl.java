package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;

@ApplicationScoped
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
	
	/**/
	
	@Override
	public String join(Collection<String> strings) {
		return __inject__(StringHelper.class).concatenate(strings.stream().map(x -> x.toString()).collect(Collectors.toList()), Constant.RESPONSE_HEADER_VALUES_SEPARATOR);
	}
	
	@Override
	public Collection<String> disjoin(String value) {
		return __inject__(CollectionHelper.class).instanciate(StringUtils.split(value, Constant.RESPONSE_HEADER_VALUES_SEPARATOR));
	}
	
	@Override
	public ResponseHelper addHeader(ResponseBuilder response, String name, Object value) {
		if(response != null && Boolean.TRUE.equals(__inject__(StringHelper.class).isNotBlank(name)) && value != null)
			response.header(name, value);
		return this;
	}
	
	@Override
	public ResponseHelper addHeader(ResponseBuilder response, String name, Collection<String> strings) {
		return addHeader(response,name,join(strings));
	}
	
	@Override
	public String getHeader(Response response, String name) {
		String value = null;
		if(response != null && Boolean.TRUE.equals(__inject__(StringHelper.class).isNotBlank(name)))
			value = response.getHeaderString(name);
		return value;
	}
	
	@Override
	public Collection<String> getHeaderAndDisjoin(Response response, String name) {
		Collection<String> strings = null;
		String value = getHeader(response, name);
		if(Boolean.TRUE.equals(__inject__(StringHelper.class).isNotBlank(value)))
			strings = disjoin(value);
		return strings;
	}
}
