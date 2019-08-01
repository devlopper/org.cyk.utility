package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.api.NodeBusiness;
import org.cyk.utility.server.persistence.api.NodePersistence;
import org.cyk.utility.server.persistence.entities.Node;

@ApplicationScoped
public class NodeBusinessImpl extends AbstractBusinessEntityImpl<Node, NodePersistence> implements NodeBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<Node> getPersistenceEntityClass() {
		return Node.class;
	}
	
}
