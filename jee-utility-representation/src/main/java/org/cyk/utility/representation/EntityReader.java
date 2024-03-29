package org.cyk.utility.representation;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path(EntityReader.PATH)
@Tag(name = EntityReader.TAG)
public interface EntityReader {

	@POST
	@Path(PATH_READ_BY_POST)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@Operation(description = "Read using post method")
	Response read(Arguments arguments);
	
	@GET
	@Path(PATH_READ_BY_GET)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@Operation(description = "Read using get method")
	Response read(
			@QueryParam(PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME)String representationEntityClassName
			,@QueryParam(PARAMETER_NAME_QUERY_IDENTIFIER) String queryIdentifier
			,@QueryParam(PARAMETER_NAME_FILTER_AS_JSON)String filterAsJson
			,@QueryParam(PARAMETER_NAME_FIRST_TUPLE_INDEX)Integer firstTupleIndex
			,@QueryParam(PARAMETER_NAME_NUMBER_OF_TUPLES)Integer numberOfTuples
			,@QueryParam(PARAMETER_NAME_COLLECTIONABLE)Boolean collectionable
			,@QueryParam(PARAMETER_NAME_QUERY_COUNTABLE)Boolean countable
		);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractExecutionImpl implements EntityReader,Serializable {
		
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String PATH = "/cyk/entity/reader";
	String PATH_READ_BY_POST = "post";
	String PATH_READ_BY_GET = "get";
	
	String TAG = "Generic Read Interface";
	
	String PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME = "representationEntityClassName";
	String PARAMETER_NAME_QUERY_IDENTIFIER = "queryIdentifier";
	String PARAMETER_NAME_FILTER_AS_JSON = "filterAsJson";
	String PARAMETER_NAME_FIRST_TUPLE_INDEX = "firstTupleIndex";
	String PARAMETER_NAME_NUMBER_OF_TUPLES = "numberOfTuples";
	String PARAMETER_NAME_COLLECTIONABLE = "collectionable";
	String PARAMETER_NAME_QUERY_COUNTABLE = "countable";
}