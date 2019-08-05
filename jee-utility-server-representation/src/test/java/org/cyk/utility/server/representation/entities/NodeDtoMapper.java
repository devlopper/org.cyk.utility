package org.cyk.utility.server.representation.entities;

import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.Nodes;
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
	
}