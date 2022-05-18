package org.cyk.utility.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

public interface MaterializedViewSpecificService<ENTITY> extends SpecificService<ENTITY> {

	@POST
	@Path("creation")
	@Produces({MediaType.TEXT_PLAIN})
	@Operation(description = "Creation of materialized view")
	@APIResponses(value = {
			@APIResponse(description = "Materialized view created",responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
			,@APIResponse(description = "Error while creating materialized view",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response create();
	
	@POST
	@Path("update")
	@Produces({MediaType.TEXT_PLAIN})
	@Operation(description = "Update of materialized view")
	@APIResponses(value = {
			@APIResponse(description = "Materialized view updated",responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
			,@APIResponse(description = "Error while updating materialized view",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response update();
	
	@POST
	@Path("actualization")
	@Produces({MediaType.TEXT_PLAIN})
	@Operation(description = "Actualiszation of materialized view")
	@APIResponses(value = {
			@APIResponse(description = "Materialized view actualized",responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
			,@APIResponse(description = "Error while actualizing materialized view",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response actualize();
	
	@POST
	@Path("deletion")
	@Produces({MediaType.TEXT_PLAIN})
	@Operation(description = "Deletion of materialized view")
	@APIResponses(value = {
			@APIResponse(description = "Materialized view deleted",responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
			,@APIResponse(description = "Error while deleting materialized view",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response delete();
	
}