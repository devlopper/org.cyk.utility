package org.cyk.utility.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.entities.MyEntityDto;

@Path(MyEntityRepresentation.PATH)
public interface MyEntityRepresentation extends RepresentationEntity<MyEntityDto> {
	
	String PATH = "/myentities/";
	
}
