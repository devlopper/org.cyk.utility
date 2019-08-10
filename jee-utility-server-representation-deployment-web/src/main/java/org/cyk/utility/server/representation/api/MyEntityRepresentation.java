package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.entities.MyEntityDtoCollection;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntity,MyEntityDto,MyEntityDtoCollection> {
	
	String PATH = "/myentities";
	
}
