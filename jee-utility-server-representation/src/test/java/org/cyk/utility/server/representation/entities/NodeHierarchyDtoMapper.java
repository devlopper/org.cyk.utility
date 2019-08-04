package org.cyk.utility.server.representation.entities;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.mapstruct.Mapper;

@Mapper
public abstract class NodeHierarchyDtoMapper extends AbstractMapperSourceDestinationImpl<NodeHierarchyDto, NodeHierarchy> {
	private static final long serialVersionUID = 1L;
    
	public NodeDto getNodeDtoCollection(Node node) {
    	return node == null ? null : DependencyInjection.inject(NodeDtoMapper.class).getSource(node);
    }
    
    public Node getNodes(NodeDto nodeDto) {
    	return nodeDto == null ? null : DependencyInjection.inject(NodeDtoMapper.class).getDestination(nodeDto);
    }
	
}