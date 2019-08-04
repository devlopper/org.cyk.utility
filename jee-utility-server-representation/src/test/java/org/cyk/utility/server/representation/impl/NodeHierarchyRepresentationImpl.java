package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.NodeHierarchyBusiness;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.NodeHierarchyRepresentation;
import org.cyk.utility.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.server.representation.entities.NodeHierarchyDtoCollection;

@ApplicationScoped
public class NodeHierarchyRepresentationImpl extends AbstractRepresentationEntityImpl<NodeHierarchy,NodeHierarchyBusiness,NodeHierarchyDto,NodeHierarchyDtoCollection> implements NodeHierarchyRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<NodeHierarchy> getPersistenceEntityClass() {
		return NodeHierarchy.class;
	}
	
}
