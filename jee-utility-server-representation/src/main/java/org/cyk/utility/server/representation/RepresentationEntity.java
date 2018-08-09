package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface RepresentationEntity<PERSISTENCE_ENTITY,ENTITY> extends RepresentationServiceProvider<PERSISTENCE_ENTITY,ENTITY> {

	/* Instantiate */
	ENTITY instantiate(PERSISTENCE_ENTITY persistenceEntity);
	Collection<ENTITY> instantiate(Collection<PERSISTENCE_ENTITY> persistenceEntities);
	PERSISTENCE_ENTITY instantiatePersistenceEntity(ENTITY entity);
	Collection<PERSISTENCE_ENTITY> instantiatePersistenceEntity(Collection<ENTITY> entities);
	PERSISTENCE_ENTITY getPersistenceEntityByIdentifier(ENTITY entity);
	Collection<PERSISTENCE_ENTITY> getPersistenceEntityByIdentifier(Collection<ENTITY> entities);
	
	/* Create */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(ENTITY dto);
	
	@POST
	@Path("/many")
	@Consumes(MediaType.APPLICATION_XML)
	Response createMany(Collection<ENTITY> dtos);
	/*
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(Collection<DTO> dtos);
	*/
	/* Get */ 
	PERSISTENCE_ENTITY findOne(Object identifier,Properties properties);
	PERSISTENCE_ENTITY findOne(Object identifier,ValueUsageType valueUsageType);
	PERSISTENCE_ENTITY findOne(Object identifier);
	PERSISTENCE_ENTITY findOneByBusinessIdentifier(Object identifier);
	
	Collection<PERSISTENCE_ENTITY> findMany(Properties properties);
	Collection<PERSISTENCE_ENTITY> findMany();
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<ENTITY> getMany();
	
	@GET
	@Path("/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	ENTITY getOne(@PathParam("identifier") Long identifier);
	
	@GET
	@Path("/system/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	ENTITY getOneBySystemIdentifier(@PathParam("identifier") Long identifier);
	
	@GET
	@Path("/business/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	ENTITY getOneByBusinessIdentifier(@PathParam("identifier") String identifier);
	
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
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<PERSISTENCE_ENTITY> getPersistenceEntityClass();
	Class<ENTITY> getRepresentationEntityClass();
	
	/**/
	
}
