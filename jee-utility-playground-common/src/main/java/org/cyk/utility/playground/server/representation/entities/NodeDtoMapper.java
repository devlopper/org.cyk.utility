package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.collection.CollectionHelperImpl;
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
	public Node getDestination(NodeDto nodeDto) {
		Node node = new Node();
		node.setIdentifier(nodeDto.getIdentifier());
		node.setCode(nodeDto.getCode());
		node.setName(nodeDto.getName());
		System.out.println("NodeDtoMapper.getDestination() "+nodeDto.getParents());
		if(nodeDto.getParents()!=null && CollectionHelperImpl.__isNotEmpty__(nodeDto.getParents().getCollection())) {
			System.out.println("NodeDtoMapper.getDestination() :: "+nodeDto.getParents().getCollection());
			for(NodeDto index : nodeDto.getParents().getCollection())
				node.addParents(getDestination(index));
		}
		return node;
	}
	
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