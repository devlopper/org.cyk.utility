package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.NodeBusiness;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.NodeRepresentation;
import org.cyk.utility.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.entities.NodeDtoCollection;

@ApplicationScoped
public class NodeRepresentationImpl extends AbstractRepresentationEntityImpl<Node,NodeBusiness,NodeDto,NodeDtoCollection> implements NodeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<Node> getPersistenceEntityClass() {
		return Node.class;
	}
	
}
