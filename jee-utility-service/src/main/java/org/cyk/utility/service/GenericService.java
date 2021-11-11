package org.cyk.utility.service;

//@Path(GenericService.PATH)
//@Tag(name = "Services génériques")
public interface GenericService extends Service {
	/*String PATH = "__generic__";
	
	@GET
	@Path("{"+PARAMETER_NAME_ELEMENT_TYPE+"}")
	@Produces({MediaType.APPLICATION_JSON})
	@Operation(description = "Obtenir une liste d'éléments")
	@APIResponses(value = {
			@APIResponse(description = "Liste d'éléments obtenue",responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON))
			,@APIResponse(description = "Erreur lors de l'obtention de la liste d'éléments",responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	Response get(
			@Parameter(name = PARAMETER_NAME_ELEMENT_TYPE,description = PARAMETER_NAME_ELEMENT_TYPE_DESCRIPTION_FRENCH)
			@PathParam(PARAMETER_NAME_ELEMENT_TYPE) String elementType
			
			,@Parameter(name = EntityReader.PARAMETER_NAME_FILTER_AS_STRING,description = EntityReader.PARAMETER_NAME_FILTER_AS_STRING_DESCRIPTION_FRENCH)
			@QueryParam(EntityReader.PARAMETER_NAME_FILTER_AS_STRING) String filterAsString
			
			,@Parameter(name = EntityReader.PARAMETER_NAME_PROJECTIONS,description = EntityReader.PARAMETER_NAME_PROJECTIONS_DESCRIPTION_FRENCH)
			@QueryParam(EntityReader.PARAMETER_NAME_PROJECTIONS) List<String> projections
			
			,@Parameter(name = EntityReader.PARAMETER_NAME_COUNTABLE,description = EntityReader.PARAMETER_NAME_COUNTABLE_DESCRIPTION_FRENCH)
			@QueryParam(EntityReader.PARAMETER_NAME_COUNTABLE) Boolean countable
			
			,@Parameter(name = EntityReader.PARAMETER_NAME_PAGEABLE,description = EntityReader.PARAMETER_NAME_PAGEABLE_DESCRIPTION_FRENCH)
			@QueryParam(EntityReader.PARAMETER_NAME_PAGEABLE)Boolean pageable
			
			,@Parameter(name = EntityReader.PARAMETER_NAME_FIRST_TUPLE_INDEX,description = EntityReader.PARAMETER_NAME_FIRST_TUPLE_INDEX_DESCRIPTION_FRENCH)
			@QueryParam(EntityReader.PARAMETER_NAME_FIRST_TUPLE_INDEX) Integer firstTupleIndex
			
			,@Parameter(name = EntityReader.PARAMETER_NAME_NUMBER_OF_TUPLES,description = EntityReader.PARAMETER_NAME_NUMBER_OF_TUPLES_DESCRIPTION_FRENCH)
			@QueryParam(EntityReader.PARAMETER_NAME_NUMBER_OF_TUPLES) Integer numberOfTuples
			);
	
	String PARAMETER_NAME_ELEMENT_TYPE = "element_type";
	String PARAMETER_NAME_ELEMENT_TYPE_DESCRIPTION_FRENCH = "Type d'élément";
	*/
}