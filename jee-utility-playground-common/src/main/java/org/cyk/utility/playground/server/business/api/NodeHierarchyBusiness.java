package org.cyk.utility.playground.server.business.api;

import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchies;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.business.hierarchy.HierarchyBusiness;

public interface NodeHierarchyBusiness extends HierarchyBusiness<NodeHierarchy,Node,NodeHierarchies> {

}
