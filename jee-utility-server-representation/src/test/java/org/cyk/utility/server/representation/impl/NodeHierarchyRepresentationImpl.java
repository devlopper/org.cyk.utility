package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.NodeHierarchyRepresentation;
import org.cyk.utility.server.representation.entities.NodeHierarchyDto;

@ApplicationScoped
public class NodeHierarchyRepresentationImpl extends AbstractRepresentationEntityImpl<NodeHierarchyDto> implements NodeHierarchyRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

}
