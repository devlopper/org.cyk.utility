package org.cyk.utility.hierarchy;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface HierarchyNode extends Objectable {

	@Override HierarchyNode setParent(Object parent);
	@Override HierarchyNode addChild(Object... child);
	
	HierarchyNode setData(Object data);
	HierarchyNodeData getData();
	
	HierarchyNode setIsCollapsible(Boolean isCollapsible);
	Boolean getIsCollapsible();
	
	HierarchyNode setIcon(Object icon);
	Object getIcon();
	
	HierarchyNode addNode(Object data);
	
	@Override HierarchyNode getParent();
	@Override HierarchyNode getLastChild();
}
