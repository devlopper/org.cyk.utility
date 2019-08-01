package org.cyk.utility.server.persistence.api;

import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.HierarchyPersistence;

public interface NodeHierarchyPersistence extends HierarchyPersistence<NodeHierarchy,Node,NodeHierarchies> {

}
