package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.playground.server.representation.entities.NodeDtoMapper;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.server.representation.hierarchy.AbstractHierarchyMapperImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class NodeHierarchyDtoMapper extends AbstractHierarchyMapperImpl<NodeHierarchyDto, NodeHierarchy,NodeDto,Node,NodeDtoMapper> {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected Class<NodeDtoMapper> __getNodeMapperClass__() {
		return NodeDtoMapper.class;
	}
}