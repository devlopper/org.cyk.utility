package org.cyk.utility.server.representation;

import javax.ws.rs.Path;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntity,MyEntityDto,MyEntityDtoCollection> {
	
	String PATH = "/myentities/";
	
}
