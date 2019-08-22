package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.Nodes;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.playground.server.representation.entities.NodeDtoCollection;
import org.cyk.utility.server.representation.hierarchy.AbstractNodeMapperImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class NodeDtoMapper extends AbstractNodeMapperImpl<NodeDto, Node,NodeDtoCollection,Nodes> {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Class<NodeDtoCollection> __getSourceCollectionClass__() {
		return NodeDtoCollection.class;
	}
	
	@Override
	protected Class<Nodes> __getDestinationCollectionClass__() {
		return Nodes.class;
	}
	
	@Override
	protected Class<Node> __getDestinationClass__() {
		return Node.class;
	}
	
}