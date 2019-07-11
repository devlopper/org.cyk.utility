package org.cyk.utility.server.representation;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Christian
 *
 */
public interface RepresentationEntity<PERSISTENCE_ENTITY,ENTITY,ENTITY_COLLECTION> extends RepresentationServiceProvider<PERSISTENCE_ENTITY,ENTITY> {

	/* Create */
	@POST
	@Path(PATH_CREATE_ONE)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	Response createOne(ENTITY entity);
	
	@POST
	@Path(PATH_CREATE_MANY)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	Response createMany(Collection<ENTITY> entities,@QueryParam(PARAMETER_PROPERTIES) String properties);
	
	@POST
	@Path(PATH_CREATE_MANY_COLLECTION)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	Response createMany(ENTITY_COLLECTION entityCollection,@QueryParam(PARAMETER_PROPERTIES) String properties);
	
	/* Read */ 
	/*@GET
	@Path(PATH_GET_MANY)
	@Produces({ MediaType.APPLICATION_XML })
	Response getMany();
	*/
	@GET
	@Path(PATH_GET_MANY)
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	Response getMany(@QueryParam(PARAMETER_IS_PAGEABLE) Boolean isPageable,@QueryParam(PARAMETER_FROM) Long from,@QueryParam(PARAMETER_COUNT) Long count,@QueryParam(PARAMETER_FIELDS) String fields
			,@QueryParam(PARAMETER_FILTERS) String filters);
	
	@GET
	@Path(PATH_GET_ONE)
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	Response getOne(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_TYPE) String type,@QueryParam(PARAMETER_FIELDS) String fields);
	
	/* Update */
	/* Using partial */
	//@PATCH FIXME Not working so we will use PUT for the moment
	@PUT
	@Path(PATH_UPDATE_ONE)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	Response updateOne(ENTITY entity,@QueryParam(PARAMETER_FIELDS) String fields);
	
	//@PATCH FIXME Not working so we will use PUT for the moment
	@PUT
	@Path(PATH_UPDATE_MANY)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	Response updateMany(Collection<ENTITY> entities,@QueryParam(PARAMETER_FIELDS) String fields);
	
	/* Delete */
	@DELETE
	@Path(PATH_DELETE_ONE)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	//Response deleteOne(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_TYPE) String type);
	Response deleteOne(ENTITY entity);
	
	@DELETE
	@Path(PATH_DELETE_MANY)
	Response deleteMany();
	
	@DELETE
	@Path(PATH_DELETE_IDENTIFIERS)
	Response deleteByIdentifiers(@QueryParam(PARAMETER_IDENTIFIER) List<String> identifiers,@QueryParam(PARAMETER_TYPE) String type);
	
	@DELETE
	@Path(PATH_DELETE_ALL)
	Response deleteAll();
	
	/* Save */
	
	@POST
	@Path(PATH_SAVE_FROM_FILE_EXCEL_SHEET)
	Response saveFromFileExcelSheet(@QueryParam(PARAMETER_WORKBOOK_NAME) String workbookName,@QueryParam(PARAMETER_SHEET_NAME) String sheetName
			,@QueryParam(PARAMETER_COLUMN_INDEX_FIELD_NAME) List<String> columnIndexFieldNames);
	
	/* Count */
	@GET
	@Path(PATH_GET_COUNT)
	Response count(@QueryParam(PARAMETER_FILTERS) String filters);
	
	/**/

	/* Instantiate */
	/*ENTITY instantiate(PERSISTENCE_ENTITY persistenceEntity);
	Collection<ENTITY> instantiate(Collection<PERSISTENCE_ENTITY> persistenceEntities);
	PERSISTENCE_ENTITY instantiatePersistenceEntity(ENTITY entity);
	Collection<PERSISTENCE_ENTITY> instantiatePersistenceEntity(Collection<ENTITY> entities);
	PERSISTENCE_ENTITY getPersistenceEntityByIdentifier(ENTITY entity);
	Collection<PERSISTENCE_ENTITY> getPersistenceEntityByIdentifier(Collection<ENTITY> entities);
	
	Class<ENTITY> getEntityClass();
	Class<PERSISTENCE_ENTITY> getPersistenceEntityClass();
	*/
	
}
