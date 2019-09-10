package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.MappingInstantiator;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.mapstruct.Mapper;

@Mapper(uses= {MappingInstantiator.class})
public abstract class PersonMapper extends AbstractMapperSourceDestinationImpl<Person, PersonDto> {
	private static final long serialVersionUID = 1L;
    	
}