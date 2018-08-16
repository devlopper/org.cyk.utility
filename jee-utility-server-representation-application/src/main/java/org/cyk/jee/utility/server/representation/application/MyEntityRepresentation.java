package org.cyk.jee.utility.server.representation.application;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntity,MyEntityDto,MyEntityDtoCollection> {
	
	String PATH = "/myentities";
	
}
