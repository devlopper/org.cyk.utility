package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.__kernel__.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonMapper extends AbstractMapperSourceDestinationImpl<Person, PersonDto> {
	private static final long serialVersionUID = 1L;
    	
}