package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.persistence.entities.MyEntity;
import org.cyk.utility.playground.server.representation.entities.MyEntityDto;
import org.cyk.utility.playground.server.representation.entities.MyEntityDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.playground.server.representation.api.MyEntityRepresentation;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntity,MyEntityDto,MyEntityDtoCollection> {
	
	String PATH = "/myentity";
	
}
