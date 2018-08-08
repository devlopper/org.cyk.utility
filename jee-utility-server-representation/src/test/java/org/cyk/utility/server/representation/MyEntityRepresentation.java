package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntity> {

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(MyEntityDto dto);
	
	@GET
	@Produces({ MediaType.APPLICATION_XML })
	Collection<MyEntityDto> getMany();
	
	String PATH = "/myentities/";
	
}
