package org.cyk.utility.playground.server.persistence.entities;

import java.util.Collection;

import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;

public interface NodeHierarchies extends Hierarchies<NodeHierarchy,Node> {

	@Override NodeHierarchies add(Collection<NodeHierarchy> privilegeTypeHierarchies);

}
