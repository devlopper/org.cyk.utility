package org.cyk.utility.client.controller.data.hierarchy;

import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;

public abstract class AbstractHierarchyMapperImpl<HIERARCHY extends Hierarchy<NODE>,HIERARCHY_DTO extends org.cyk.utility.server.representation.hierarchy.AbstractHierarchy<?>,NODE extends DataIdentifiedByString> extends AbstractMapperSourceDestinationImpl<HIERARCHY, HIERARCHY_DTO> {
	private static final long serialVersionUID = 1L;
    	
}