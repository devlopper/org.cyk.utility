package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.api.NodeHierarchyPersistence;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchyPersistenceImpl;

@ApplicationScoped
public class NodeHierarchyPersistenceImpl extends AbstractHierarchyPersistenceImpl<NodeHierarchy,Node,NodeHierarchies> implements NodeHierarchyPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}
