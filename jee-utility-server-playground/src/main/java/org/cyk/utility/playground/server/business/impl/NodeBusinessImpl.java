package org.cyk.utility.playground.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.NodeBusiness;
import org.cyk.utility.playground.server.business.api.NodeHierarchyBusiness;
import org.cyk.utility.playground.server.persistence.api.NodeHierarchyPersistence;
import org.cyk.utility.playground.server.persistence.api.NodePersistence;
import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.business.hierarchy.AbstractBusinessIdentifiedByStringAndCodedImpl;

@ApplicationScoped
public class NodeBusinessImpl extends AbstractBusinessIdentifiedByStringAndCodedImpl<Node, NodePersistence,NodeHierarchy,NodeHierarchies,NodeHierarchyPersistence,NodeHierarchyBusiness> implements NodeBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
}
