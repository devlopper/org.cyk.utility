package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.__kernel__.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonTypeMapper extends AbstractMapperSourceDestinationImpl<PersonType, PersonTypeDto> {
	private static final long serialVersionUID = 1L;
    	
}