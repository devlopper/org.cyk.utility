package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.NodeBusiness;
import org.cyk.utility.server.business.api.NodeHierarchyBusiness;
import org.cyk.utility.server.business.hierarchy.AbstractBusinessIdentifiedByStringAndCodedImpl;
import org.cyk.utility.server.persistence.api.NodeHierarchyPersistence;
import org.cyk.utility.server.persistence.api.NodePersistence;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;

@ApplicationScoped
public class NodeBusinessImpl extends AbstractBusinessIdentifiedByStringAndCodedImpl<Node, NodePersistence,NodeHierarchy,NodeHierarchies,NodeHierarchyPersistence,NodeHierarchyBusiness> implements NodeBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Class<Node> getPersistenceEntityClass() {
		return Node.class;
	}

	@Override
	protected Class<NodeHierarchy> __getHierarchyClass__() {
		return NodeHierarchy.class;
	}

	@Override
	protected Class<NodeHierarchyBusiness> __getHierarchyBusinessClass__() {
		return NodeHierarchyBusiness.class;
	}

	@Override
	protected Class<NodeHierarchyPersistence> __getHierarchyPersistenceClass__() {
		return NodeHierarchyPersistence.class;
	}
	
}
