package org.cyk.utility.server.representation.entities;

import org.cyk.utility.mapping.MapperSourceDestination;
import org.cyk.utility.server.persistence.entities.Node;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NodeDtoMapper extends MapperSourceDestination<NodeDto, Node> {
    NodeDtoMapper INSTANCE = Mappers.getMapper(NodeDtoMapper.class);
 
}