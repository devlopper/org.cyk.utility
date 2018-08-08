package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface RepresentationEntity<ENTITY,DTO> extends RepresentationServiceProvider<ENTITY> {

	/* Create */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(DTO dto);
	
	/* Find */ 
	ENTITY findOne(Object identifier,Properties properties);
	ENTITY findOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY findOne(Object identifier);
	ENTITY findOneByBusinessIdentifier(Object identifier);
	
	Collection<ENTITY> findMany(Properties properties);
	Collection<ENTITY> findMany();
	
	/* Update */
	
	/* Delete */
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<ENTITY> getEntityClass();
	
	/**/
	
}
