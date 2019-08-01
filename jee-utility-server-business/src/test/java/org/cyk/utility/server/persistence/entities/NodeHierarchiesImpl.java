package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchiesImpl;

public class NodeHierarchiesImpl extends AbstractHierarchiesImpl<NodeHierarchy,Node> implements NodeHierarchies,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NodeHierarchies add(Collection<NodeHierarchy> collection) {
		return (NodeHierarchies) super.add(collection);
	}

}
