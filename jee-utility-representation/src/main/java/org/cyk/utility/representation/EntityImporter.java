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

@Path(EntityImporter.PATH)
@Tag(name = EntityImporter.TAG)
public interface EntityImporter {

	@POST
	@Path(PATH_READ_BY_POST)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@Operation(description = "Import using post method")
	Response import_(Arguments arguments);
	
	@GET
	@Path(PATH_READ_BY_GET)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@Operation(description = "Import using get method")
	Response import_(@QueryParam(PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME)String representationEntityClassName);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractExecutionImpl implements EntityImporter,Serializable {
		
	}
	
	/**/
	
	static EntityImporter getInstance() {
		return Helper.getInstance(EntityImporter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String PATH = "/cyk/entity/importer";
	String PATH_READ_BY_POST = "post";
	String PATH_READ_BY_GET = "get";
	
	String TAG = "Generic Import Interface";
	
	String PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME = "representationEntityClassName";
}