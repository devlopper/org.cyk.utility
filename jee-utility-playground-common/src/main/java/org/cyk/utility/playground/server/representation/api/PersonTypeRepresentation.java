package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDto;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(PersonTypeRepresentation.PATH)
public interface PersonTypeRepresentation extends RepresentationEntity<PersonType,PersonTypeDto,PersonTypeDtoCollection> {
	
	String PATH = "/persontype";
	
}
