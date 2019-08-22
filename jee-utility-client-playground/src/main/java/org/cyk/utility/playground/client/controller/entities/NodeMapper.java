package org.cyk.utility.playground.client.controller.entities;

import java.util.Collection;

import org.cyk.utility.client.controller.data.MappingInstantiator;
import org.cyk.utility.client.controller.data.hierarchy.AbstractNodeMapperImpl;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.playground.server.representation.entities.NodeDtoCollection;
import org.mapstruct.Mapper;

@Mapper(uses= {MappingInstantiator.class})
public abstract class NodeMapper extends AbstractNodeMapperImpl<Node, NodeDto,Collection<Object>,NodeDtoCollection> {
	private static final long serialVersionUID = 1L;
 
	@Override
	protected Class<NodeDtoCollection> __getDestinationCollectionClass__() {
		return NodeDtoCollection.class;
	}
	
	@Override
	protected Class<NodeDto> __getDestinationClass__() {
		return NodeDto.class;
	}
	
}