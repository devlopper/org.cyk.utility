package org.cyk.utility.client.controller.data.hierarchy;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;

public abstract class AbstractHierarchyMapperImpl<HIERARCHY extends Hierarchy<NODE>,HIERARCHY_DTO extends org.cyk.utility.server.representation.hierarchy.AbstractHierarchy<?>,NODE extends DataIdentifiedByString<?>> extends MapperSourceDestination.AbstractImpl<HIERARCHY, HIERARCHY_DTO> {
	private static final long serialVersionUID = 1L;
    
	@Override
	public HIERARCHY getSource(HIERARCHY_DTO destination) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HIERARCHY_DTO getDestination(HIERARCHY source) {
		// TODO Auto-generated method stub
		return null;
	}
	
}