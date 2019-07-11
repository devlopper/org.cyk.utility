package org.cyk.utility.server.representation.entities;

import org.cyk.utility.mapping.MapperSourceDestination;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MyEntityDtoMapper extends MapperSourceDestination<MyEntityDto, MyEntity> {
    MyEntityDtoMapper INSTANCE = Mappers.getMapper(MyEntityDtoMapper.class);
 
}