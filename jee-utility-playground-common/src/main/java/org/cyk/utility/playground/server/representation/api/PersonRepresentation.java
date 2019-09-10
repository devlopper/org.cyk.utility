package org.cyk.utility.playground.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.cyk.utility.playground.server.representation.entities.PersonDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.playground.server.representation.api.PersonRepresentation;

@Path(PersonRepresentation.PATH)
public interface PersonRepresentation extends RepresentationEntity<Person,PersonDto,PersonDtoCollection> {
	
	String PATH = "/person";
	
}
