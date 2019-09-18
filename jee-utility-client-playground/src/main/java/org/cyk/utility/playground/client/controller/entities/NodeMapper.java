package org.cyk.utility.playground.client.controller.entities;

import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.client.controller.data.MappingInstantiator;
import org.cyk.utility.client.controller.data.hierarchy.AbstractNodeMapperImpl;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.playground.server.representation.entities.NodeDtoCollection;
import org.mapstruct.Mapper;

@Mapper(uses= {MappingInstantiator.class})
public abstract class NodeMapper extends AbstractNodeMapperImpl<Node, NodeDto,NodeDtoCollection> {
	private static final long serialVersionUID = 1L;

	@Override
	public NodeDto getDestination(Node node) {
		NodeDto nodeDto = new NodeDto();
		nodeDto.setIdentifier(node.getIdentifier());
		nodeDto.setCode(node.getCode());
		nodeDto.setName(node.getName());
		nodeDto.set__actions__(node.get__actions__());
		nodeDto.setNumberOfChildren(node.getNumberOfChildren());
		nodeDto.setNumberOfParents(node.getNumberOfParents());
		
		if(CollectionHelperImpl.__isNotEmpty__(node.getParents())) {
			for(Node index : node.getParents()) {
				nodeDto.addParents(new NodeDto().setIdentifier(index.getIdentifier()).setCode(index.getCode()));
			}
			/*nodeDto.setParents(new NodeDtoCollection());
			nodeDto.getParents().add(node.get__parents__().stream().map(x -> new NodeDto()
					.setIdentifier(x.getIdentifier()).setCode(x.getCode())).collect(Collectors.toList()));
			*/
		}
		System.out.println("NodeMapper.getDestination() : "+nodeDto.getParents());
		return nodeDto;
	}
	
	@Override
	public Node getSource(NodeDto nodeDto) {
		Node node = DependencyInjection.inject(Node.class);
		node.setIdentifier(nodeDto.getIdentifier());
		node.setCode(nodeDto.getCode());
		node.setName(nodeDto.getName());
		node.set__actions__(nodeDto.get__actions__());
		node.setNumberOfChildren(nodeDto.getNumberOfChildren());
		node.setNumberOfParents(nodeDto.getNumberOfParents());
		
		if(nodeDto.getParents()!=null && CollectionHelperImpl.__isNotEmpty__(nodeDto.getParents().getCollection())) {
			for(NodeDto index : nodeDto.getParents().getCollection())
				node.addParents(DependencyInjection.inject(Node.class).setIdentifier(index.getIdentifier()).setCode(index.getCode()).setName(index.getName()));
			System.out.println("NodeMapper.getSource() ::: "+node.getParents());
			/*nodeDto.setParents(new NodeDtoCollection());
			nodeDto.getParents().add(node.get__parents__().stream().map(x -> new NodeDto()
					.setIdentifier(x.getIdentifier()).setCode(x.getCode())).collect(Collectors.toList()));
			*/
		}		
		return node;
	}
	
}