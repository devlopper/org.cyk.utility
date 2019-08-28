package org.cyk.utility.playground.server.representation.entities;

import org.cyk.utility.playground.server.persistence.entities.SelectedNode;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper(uses= {NodeDtoMapper.class})
public abstract class SelectedNodeDtoMapper extends AbstractMapperSourceDestinationImpl<SelectedNodeDto, SelectedNode> {
    private static final long serialVersionUID = 1L;
	
}