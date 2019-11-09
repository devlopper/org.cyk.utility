package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntityDto> {
	
	String PATH = "/myentity";
	
}
