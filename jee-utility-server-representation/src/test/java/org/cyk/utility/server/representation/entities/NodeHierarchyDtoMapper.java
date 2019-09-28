package org.cyk.utility.server.representation.entities;

import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class NodeHierarchyDtoMapper extends AbstractMapperSourceDestinationImpl<NodeHierarchyDto, NodeHierarchy> {
	private static final long serialVersionUID = 1L;
    
}