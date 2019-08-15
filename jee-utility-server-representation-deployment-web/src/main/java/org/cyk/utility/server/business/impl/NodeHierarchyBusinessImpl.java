package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.NodeHierarchyBusiness;
import org.cyk.utility.server.business.hierarchy.AbstractHierarchyBusinessImpl;
import org.cyk.utility.server.persistence.api.NodeHierarchyPersistence;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;

@ApplicationScoped
public class NodeHierarchyBusinessImpl extends AbstractHierarchyBusinessImpl<NodeHierarchy, NodeHierarchyPersistence,Node,NodeHierarchies> implements NodeHierarchyBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
