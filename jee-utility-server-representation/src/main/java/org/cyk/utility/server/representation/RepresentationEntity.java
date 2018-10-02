package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
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
	@Path(PATH_ROOT)
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(ENTITY entity);
	
	@POST
	@Path(PATH_MANY)
	@Consumes(MediaType.APPLICATION_XML)
	Response createMany(Collection<ENTITY> entities);
	
	@POST
	@Path(PATH_MANY_COLLECTION)
	@Consumes(MediaType.APPLICATION_XML)
	Response createMany(ENTITY_COLLECTION entityCollection);
	
	/* Read */ 
	@GET
	@Path(PATH_ROOT)
	@Produces({ MediaType.APPLICATION_XML })
	Response getMany();
	
	@GET
	@Path(PATH_IDENTIFIER)
	@Produces({ MediaType.APPLICATION_XML })
	Response getOne(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_TYPE) String type);
	
	/* Update */
	/* Using partial */
	@PATCH
	@Path(PATH_ROOT)
	@Consumes(MediaType.APPLICATION_XML)
	Response updateOne(ENTITY entity,@QueryParam(PARAMETER_FIELDS) String fields);
	
	@PATCH
	@Path(PATH_MANY)
	@Consumes(MediaType.APPLICATION_XML)
	Response updateMany(Collection<ENTITY> entities,@QueryParam(PARAMETER_FIELDS) String fields);
	
	/* Delete */
	@DELETE
	@Path(PATH_IDENTIFIER)
	@Produces(MediaType.APPLICATION_XML)
	Response deleteOne(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_TYPE) String type);
	
	@DELETE
	@Path(PATH_MANY)
	@Produces(MediaType.APPLICATION_XML)
	Response deleteMany();
	
	@DELETE
	@Path(PATH_DELETE_ALL)
	@Produces(MediaType.APPLICATION_XML)
	Response deleteAll();
	
	/* Count */
	@GET
	@Path(PATH_GET_COUNT)
	@Produces(MediaType.TEXT_PLAIN)
	Response count();
	
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
