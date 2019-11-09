package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.representation.api.NodeRepresentation;
import org.cyk.utility.playground.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class NodeRepresentationImpl extends AbstractRepresentationEntityImpl<NodeDto> implements NodeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
