package org.cyk.utility.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

public interface SpecificService<ENTITY> extends Service {

	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	@Operation(description = "Obtenir une liste")
	@APIResponses(value = {
			@APIResponse(description = "Une liste obtenue",responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
			,@APIResponse(description = "Erreur lors de l'obtention d'une liste",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response get(
			@Parameter(name = PARAMETER_NAME_FILTER,description = PARAMETER_NAME_FILTER_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_FILTER) String filter
			
			,@Parameter(name = PARAMETER_NAME_FILTER_FORMAT,description = PARAMETER_NAME_FILTER_FORMAT_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_FILTER_FORMAT) FilterFormat filterFormat
			
			,@Parameter(name = PARAMETER_NAME_DEFAULTABLE,description = PARAMETER_NAME_DEFAULTABLE_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_DEFAULTABLE) Boolean defaultable
			
			,@Parameter(name = PARAMETER_NAME_PROJECTIONS,description = PARAMETER_NAME_PROJECTIONS_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_PROJECTIONS) List<String> projections
			
			,@Parameter(name = PARAMETER_NAME_COUNTABLE,description = PARAMETER_NAME_COUNTABLE_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_COUNTABLE) Boolean countable
			
			,@Parameter(name = PARAMETER_NAME_PAGEABLE,description = PARAMETER_NAME_PAGEABLE_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_PAGEABLE)Boolean pageable
			
			,@Parameter(name = PARAMETER_NAME_FIRST_TUPLE_INDEX,description = PARAMETER_NAME_FIRST_TUPLE_INDEX_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_FIRST_TUPLE_INDEX) Integer firstTupleIndex
			
			,@Parameter(name = PARAMETER_NAME_NUMBER_OF_TUPLES,description = PARAMETER_NAME_NUMBER_OF_TUPLES_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_NUMBER_OF_TUPLES) Integer numberOfTuples
			);
	
	@POST
	@Path("get")
	@Produces({MediaType.APPLICATION_JSON})
	@Operation(description = "Obtenir une liste")
	@APIResponses(value = {
			@APIResponse(description = "Une liste obtenue",responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
			,@APIResponse(description = "Erreur lors de l'obtention d'une liste",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response getUsingPost(
			@Parameter(name = PARAMETER_NAME_FILTER,description = PARAMETER_NAME_FILTER_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_FILTER) String filter
			
			,@Parameter(name = PARAMETER_NAME_FILTER_FORMAT,description = PARAMETER_NAME_FILTER_FORMAT_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_FILTER_FORMAT) FilterFormat filterFormat
			
			,@Parameter(name = PARAMETER_NAME_DEFAULTABLE,description = PARAMETER_NAME_DEFAULTABLE_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_DEFAULTABLE) Boolean defaultable
			
			,@Parameter(name = PARAMETER_NAME_PROJECTIONS,description = PARAMETER_NAME_PROJECTIONS_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_PROJECTIONS) List<String> projections
			
			,@Parameter(name = PARAMETER_NAME_COUNTABLE,description = PARAMETER_NAME_COUNTABLE_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_COUNTABLE) Boolean countable
			
			,@Parameter(name = PARAMETER_NAME_PAGEABLE,description = PARAMETER_NAME_PAGEABLE_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_PAGEABLE)Boolean pageable
			
			,@Parameter(name = PARAMETER_NAME_FIRST_TUPLE_INDEX,description = PARAMETER_NAME_FIRST_TUPLE_INDEX_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_FIRST_TUPLE_INDEX) Integer firstTupleIndex
			
			,@Parameter(name = PARAMETER_NAME_NUMBER_OF_TUPLES,description = PARAMETER_NAME_NUMBER_OF_TUPLES_DESCRIPTION_FRENCH)
			@FormParam(PARAMETER_NAME_NUMBER_OF_TUPLES) Integer numberOfTuples
			);
	
	@GET
	@Path("{"+PARAMETER_NAME_IDENTIFIER+"}")
	@Produces({MediaType.APPLICATION_JSON})
	@Operation(description = "Obtenir un élément par son identifiant")
	@APIResponses(value = {
			@APIResponse(description = "Un élément obtenu",responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
			,@APIResponse(description = "Erreur lors de l'obtention d'un élément par son identifiant",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response getByIdentifier(
			@Parameter(name = PARAMETER_NAME_IDENTIFIER,description = PARAMETER_NAME_IDENTIFIER_DESCRIPTION_FRENCH)
			@PathParam(PARAMETER_NAME_IDENTIFIER) String identifier
			, @Parameter(name = PARAMETER_NAME_PROJECTIONS,description = PARAMETER_NAME_PROJECTIONS_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_PROJECTIONS) List<String> projections
			);
	
	@GET
	@Path("nombre")
	@Produces({MediaType.TEXT_PLAIN})
	@Operation(description = "Obtenir un nombre d'élément")
	@APIResponses(value = {
			@APIResponse(description = "Un nombre d'élément obtenu",responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
			,@APIResponse(description = "Erreur lors de l'obtention d'un nombre d'élément",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response count(
			@Parameter(name = PARAMETER_NAME_FILTER,description = PARAMETER_NAME_FILTER_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_FILTER) String filter
			
			,@Parameter(name = PARAMETER_NAME_FILTER_FORMAT,description = PARAMETER_NAME_FILTER_FORMAT_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_FILTER_FORMAT) FilterFormat filterFormat
			
			,@Parameter(name = PARAMETER_NAME_DEFAULTABLE,description = PARAMETER_NAME_DEFAULTABLE_DESCRIPTION_FRENCH)
			@QueryParam(PARAMETER_NAME_DEFAULTABLE) Boolean defaultable
			);
}