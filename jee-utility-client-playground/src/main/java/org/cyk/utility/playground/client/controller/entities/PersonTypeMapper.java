package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonTypeMapper extends MapperSourceDestination.AbstractImpl<PersonType, PersonTypeDto> {
	private static final long serialVersionUID = 1L;
    	
}