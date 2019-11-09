package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.PersonTypeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(PersonTypeRepresentation.PATH)
public interface PersonTypeRepresentation extends RepresentationEntity<PersonTypeDto> {
	
	String PATH = "/persontype";
	
}
