package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
public interface RepresentationEntity<ENTITY,DTO> extends RepresentationServiceProvider<ENTITY,DTO> {

	/* Create */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(DTO dto);
	/*
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(Collection<DTO> dtos);
	*/
	/* Find */ 
	ENTITY findOne(Object identifier,Properties properties);
	ENTITY findOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY findOne(Object identifier);
	ENTITY findOneByBusinessIdentifier(Object identifier);
	
	Collection<ENTITY> findMany(Properties properties);
	Collection<ENTITY> findMany();
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<DTO> getMany();
	
	/* Update */
	
	/* Delete */
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<ENTITY> getEntityClass();
	
	/**/
	
}
