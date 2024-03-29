package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.playground.server.representation.entities.NamableDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class NamableMapper extends MapperSourceDestination.AbstractImpl<Namable, NamableDto> {
	private static final long serialVersionUID = 1L;
    	
}