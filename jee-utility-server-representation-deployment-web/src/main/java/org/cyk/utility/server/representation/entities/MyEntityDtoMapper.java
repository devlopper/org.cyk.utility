package org.cyk.utility.server.representation.entities;

import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class MyEntityDtoMapper extends AbstractMapperSourceDestinationImpl<MyEntityDto, MyEntity> {
    private static final long serialVersionUID = 1L;
	
}