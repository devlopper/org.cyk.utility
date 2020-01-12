package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.playground.server.persistence.entities.Namable;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class NamableDtoMapper extends AbstractMapperSourceDestinationImpl<NamableDto, Namable> {
    private static final long serialVersionUID = 1L;
	
}