package org.cyk.utility.playground.client.controller.entities;

import org.cyk.utility.client.controller.data.MappingInstantiator;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.playground.server.representation.entities.SelectedNodeDto;
import org.mapstruct.Mapper;

@Mapper(uses= {MappingInstantiator.class,NodeMapper.class})
public abstract class SelectedNodeMapper extends AbstractMapperSourceDestinationImpl<SelectedNode, SelectedNodeDto> {
	private static final long serialVersionUID = 1L;
    	
}