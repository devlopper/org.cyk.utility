package org.cyk.utility.server.representation.deployment.web;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntity,MyEntityDto,MyEntityDtoCollection> {
	
	String PATH = "/myentities";
	
}
