package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.api.NodeHierarchyBusiness;
import org.cyk.utility.server.persistence.api.NodeHierarchyPersistence;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;

@ApplicationScoped
public class NodeHierarchyBusinessImpl extends AbstractBusinessEntityImpl<NodeHierarchy, NodeHierarchyPersistence> implements NodeHierarchyBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<NodeHierarchy> getPersistenceEntityClass() {
		return NodeHierarchy.class;
	}
	
}
