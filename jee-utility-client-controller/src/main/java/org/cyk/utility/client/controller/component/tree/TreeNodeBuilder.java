package org.cyk.utility.client.controller.component.tree;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.hierarchy.HierarchyNode;

public interface TreeNodeBuilder extends VisibleComponentBuilder<TreeNode> {

	HierarchyNode getHierarchyNode();
	HierarchyNode getHierarchyNode(Boolean injectIfNull);
	TreeNodeBuilder setHierarchyNode(HierarchyNode hierarchyNode);
	TreeNodeBuilder setHierarchyNodeProperty(Object key,Object value);
	
	String getFamily();
	TreeNodeBuilder setFamily(String family);
	
	Object getData();
	TreeNodeBuilder setData(Object data);
	
	Boolean getIsCollapsible();
	TreeNodeBuilder setIsCollapsible(Boolean isCollapsible);
	
	Boolean getIsDroppable();
	TreeNodeBuilder setIsDroppable(Boolean isDroppable);
	
	Boolean getIsDraggable();
	TreeNodeBuilder setIsDraggable(Boolean isDraggable);
	
	Boolean getIsSelectable();
	TreeNodeBuilder setIsSelectable(Boolean isSelectable);
	
	Boolean getIsExpanded();
	TreeNodeBuilder setIsExpanded(Boolean isExpanded);
	
	TreeNodeBuilder addNode(Object data);
}
