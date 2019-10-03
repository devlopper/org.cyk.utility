package org.cyk.utility.server.representation.entities;

import org.cyk.utility.server.persistence.entities.ParentChild;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ParentChildDtoMapper extends AbstractMapperSourceDestinationImpl<ParentChildDto, ParentChild> {
    private static final long serialVersionUID = 1L;
	
}