package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonDtoMapper extends AbstractMapperSourceDestinationImpl<PersonDto, Person> {
    private static final long serialVersionUID = 1L;
	
}