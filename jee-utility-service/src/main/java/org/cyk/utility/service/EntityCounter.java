package org.cyk.utility.service;

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

@Path(EntityCounter.PATH)
public interface EntityCounter {

	@POST
	@Path(PATH_COUNT_BY_POST)
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON})
	Response count(Arguments arguments);
	
	@GET
	@Path(PATH_COUNT_BY_GET)
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON})
	Response count(
			@QueryParam(EntityReader.PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME)String representationEntityClassName
			,@QueryParam(EntityReader.PARAMETER_NAME_QUERY_IDENTIFIER) String queryIdentifier
			,@QueryParam(EntityReader.PARAMETER_NAME_FILTER_AS_JSON)String filterAsJson
		);
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String TAG = "Generic Count Interface";
	
	String PATH = "/cyk/entity/counter";
	String PATH_COUNT_BY_POST = "post";
	String PATH_COUNT_BY_GET = "get";
}