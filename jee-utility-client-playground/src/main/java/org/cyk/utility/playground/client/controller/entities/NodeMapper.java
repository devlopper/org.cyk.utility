package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.client.controller.data.MappingInstantiator;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.mapstruct.Mapper;

@Mapper(uses= {MappingInstantiator.class})
public abstract class NodeMapper extends MapperSourceDestination.AbstractImpl<Node, NodeDto> {
	private static final long serialVersionUID = 1L;
	
}