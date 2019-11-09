package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.NodeRepresentation;
import org.cyk.utility.server.representation.entities.NodeDto;

@ApplicationScoped
public class NodeRepresentationImpl extends AbstractRepresentationEntityImpl<NodeDto> implements NodeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
