package org.cyk.utility.server.representation.entities;

import org.cyk.utility.server.persistence.entities.Child;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ChildDtoMapper extends AbstractMapperSourceDestinationImpl<ChildDto, Child> {
    private static final long serialVersionUID = 1L;
	
}