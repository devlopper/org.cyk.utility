package org.cyk.utility.server.representation;

import java.util.Collection;

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
public interface RepresentationEntity<PERSISTENCE_ENTITY,ENTITY> extends RepresentationServiceProvider<PERSISTENCE_ENTITY,ENTITY> {

	/* Create */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(ENTITY dto);
	
	@POST
	@Path("/many")
	@Consumes(MediaType.APPLICATION_XML)
	Response createMany(Collection<ENTITY> dtos);
	
	/* Read */ 
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<ENTITY> getMany();
	
	@GET
	@Path("/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	ENTITY getOne(@PathParam("identifier") String identifier,@QueryParam("type") String type);
	
	/* Update */
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	Response updateOne(ENTITY dto);
	
	@PUT
	@Path("/many")
	@Consumes(MediaType.APPLICATION_XML)
	Response updateMany(Collection<ENTITY> dtos);
	
	/* Delete */
	@DELETE
	@Path("/{identifier}")
	@Produces(MediaType.APPLICATION_XML)
	Response deleteOne(@PathParam("identifier") String identifier,@QueryParam("type") String type);
	
	@DELETE
	@Path("/many")
	@Produces(MediaType.APPLICATION_XML)
	Response deleteMany();
	
	/* Count */
	@GET
	@Path(GET+"count")
	@Produces(MediaType.TEXT_PLAIN)
	Long count();
	
	/**/

	/* Instantiate */
	ENTITY instantiate(PERSISTENCE_ENTITY persistenceEntity);
	Collection<ENTITY> instantiate(Collection<PERSISTENCE_ENTITY> persistenceEntities);
	PERSISTENCE_ENTITY instantiatePersistenceEntity(ENTITY entity);
	Collection<PERSISTENCE_ENTITY> instantiatePersistenceEntity(Collection<ENTITY> entities);
	PERSISTENCE_ENTITY getPersistenceEntityByIdentifier(ENTITY entity);
	Collection<PERSISTENCE_ENTITY> getPersistenceEntityByIdentifier(Collection<ENTITY> entities);
	
	Class<PERSISTENCE_ENTITY> getPersistenceEntityClass();
	Class<ENTITY> getRepresentationEntityClass();
	
	/**/
	
	String GET = "/get/";
	String OPERATION = "/operation";
}
