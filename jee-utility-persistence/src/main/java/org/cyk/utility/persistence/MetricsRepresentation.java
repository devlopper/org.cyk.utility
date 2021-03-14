package org.cyk.utility.persistence;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path(MetricsRepresentation.PATH)
@Tag(name = "Persistence Metrics")
public interface MetricsRepresentation extends org.cyk.utility.__kernel__.representation.Representation {

	@GET
	@Path(PATH_GET)
	@Operation(description = "Get metrics")
	@APIResponses(value = {
		@APIResponse(responseCode = "200")	
	})
	Response get();
	
	@GET
	@Path(PATH_GET_AS_MAP)
	@Operation(description = "Get metrics")
	@APIResponses(value = {
		@APIResponse(responseCode = "200")	
	})
	Map<String,String> getAsMap();
	
	@POST
	@Path(PATH_ENABLE)
	@Operation(description = "Enable metrics")
	@APIResponses(value = {
		@APIResponse(responseCode = "200")	
	})
	Response enable();
	
	@POST
	@Path(PATH_DISABLE)
	@Operation(description = "Disable metrics")
	@APIResponses(value = {
		@APIResponse(responseCode = "200")	
	})
	Response disable();
	
	@GET
	@Path(PATH_GET_ENABLED_AS_BOOLEAN)
	@Operation(description = "Get enabled status")
	@APIResponses(value = {
		@APIResponse(responseCode = "200")	
	})
	Boolean getEnabledAsBoolean();
	
	String PATH = "/persistencemetrics";
	String PATH_GET = __SLASH__+"get";
	String PATH_GET_AS_MAP = __SLASH__+"getasmap";
	String PATH_GET_ENABLED_AS_BOOLEAN = __SLASH__+"enabled";
	
	String PATH_ENABLE = __SLASH__+"enable";
	String PATH_DISABLE = __SLASH__+"disable";
}