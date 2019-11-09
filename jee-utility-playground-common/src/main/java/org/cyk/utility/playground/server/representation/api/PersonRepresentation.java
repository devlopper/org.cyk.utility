package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(PersonRepresentation.PATH)
public interface PersonRepresentation extends RepresentationEntity<PersonDto> {
	
	String PATH = "/person";
	
}
