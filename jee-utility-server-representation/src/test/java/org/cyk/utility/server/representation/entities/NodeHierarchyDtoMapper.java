package org.cyk.utility.server.representation.entities;

import org.cyk.utility.mapping.MapperSourceDestination;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NodeHierarchyDtoMapper extends MapperSourceDestination<NodeHierarchyDto, NodeHierarchy> {
    NodeHierarchyDtoMapper INSTANCE = Mappers.getMapper(NodeHierarchyDtoMapper.class);
 
}