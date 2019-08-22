package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.NodeHierarchyBusiness;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.playground.server.representation.api.NodeHierarchyRepresentation;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDto;
import org.cyk.utility.playground.server.representation.entities.NodeHierarchyDtoCollection;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class NodeHierarchyRepresentationImpl extends AbstractRepresentationEntityImpl<NodeHierarchy,NodeHierarchyBusiness,NodeHierarchyDto,NodeHierarchyDtoCollection> implements NodeHierarchyRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

}
