package org.cyk.utility.client.controller.component.tree;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.hierarchy.HierarchyNode;

public interface TreeNode extends VisibleComponent {

	@Override TreeNode setParent(Object parent);
	@Override TreeNode addChild(Object... child);
	
	TreeNode addChild(String family,Object data);
	
	HierarchyNode getHierarchyNode();
	TreeNode setHierarchyNode(HierarchyNode hierarchyNode);
	
	String getFamily();
	TreeNode setFamily(String family);
	
	Object getData();
	TreeNode setData(Object data);
	
	Boolean getIsCollapsible();
	TreeNode setIsCollapsible(Boolean isCollapsible);
	
	Boolean getIsDroppable();
	TreeNode setIsDroppable(Boolean isDroppable);
	
	Boolean getIsDraggable();
	TreeNode setIsDraggable(Boolean isDraggable);
	
	Boolean getIsSelectable();
	TreeNode setIsSelectable(Boolean isSelectable);
	
	Boolean getIsExpanded();
	TreeNode setIsExpanded(Boolean isExpanded);
	
	/**/
	
	String PROPERTY_FAMILY = "family";
	String PROPERTY_DATA = "data";
	String PROPERTY_IS_COLLAPSIBLE = "isCollapsible";
	String PROPERTY_IS_DROPPABLE = "isDroppable";
	String PROPERTY_IS_DRAGGABLE = "isDraggable";
	String PROPERTY_IS_SELECTABLE = "isSelectable";
	String PROPERTY_IS_EXPANDED = "isExpanded";
}
