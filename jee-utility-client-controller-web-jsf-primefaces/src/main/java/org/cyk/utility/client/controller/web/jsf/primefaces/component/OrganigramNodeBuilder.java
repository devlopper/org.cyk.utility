package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import org.cyk.utility.client.controller.component.tree.TreeNode;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.primefaces.model.OrganigramNode;

public interface OrganigramNodeBuilder extends ComponentBuilder<OrganigramNode,TreeNode> {

	HierarchyNode getHierarchyNode();
	HierarchyNode getHierarchyNode(Boolean injectIfNull);
	OrganigramNodeBuilder setHierarchyNode(HierarchyNode hierarchyNode);
	
	@Override OrganigramNodeBuilder setParent(Object parent);
	@Override OrganigramNodeBuilder setModel(TreeNode model);
	
}
