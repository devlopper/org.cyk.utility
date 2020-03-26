package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonMapper extends MapperSourceDestination.AbstractImpl<Person, PersonDto> {
	private static final long serialVersionUID = 1L;
    	
}