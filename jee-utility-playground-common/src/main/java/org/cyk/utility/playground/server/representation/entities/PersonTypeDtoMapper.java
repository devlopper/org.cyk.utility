package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonTypeDtoMapper extends AbstractMapperSourceDestinationImpl<PersonTypeDto, PersonType> {
    private static final long serialVersionUID = 1L;
	
}