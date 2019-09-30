package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.MappingInstantiator;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDto;
import org.mapstruct.Mapper;

@Mapper(uses= {MappingInstantiator.class})
public abstract class NodeHierarchyMapper extends AbstractMapperSourceDestinationImpl<NodeHierarchy, NodeHierarchyDto> {
	private static final long serialVersionUID = 1L;
	
}