package org.cyk.utility.server.representation.entities;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.Nodes;
import org.mapstruct.Mapper;

@Mapper
public abstract class NodeDtoMapper extends AbstractMapperSourceDestinationImpl<NodeDto, Node> {
	private static final long serialVersionUID = 1L;
	
    public NodeDtoCollection getNodeDtoCollection(Nodes nodes) {
    	NodeDtoCollection nodeDtoCollection = null;
    	if(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(nodes)) {
    		nodeDtoCollection = new NodeDtoCollection();
    		for(Node index : nodes.get())
    			nodeDtoCollection.add(getSource(index));
    	}
    	return nodeDtoCollection;
    }
    
    public Nodes getNodes(NodeDtoCollection nodeDtoCollection) {
    	Nodes nodes = null;
    	if(nodeDtoCollection!= null && DependencyInjection.inject(CollectionHelper.class).isNotEmpty(nodeDtoCollection.getCollection())) {
    		nodes = DependencyInjection.inject(Nodes.class);
    		for(NodeDto index : nodeDtoCollection.getCollection())
    			nodes.add(getDestination(index));
    	}
    	return nodes;
    }
}