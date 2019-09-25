package org.cyk.utility.hierarchy;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.object.Objects;

public interface HierarchyNode extends Objectable {

	@Override HierarchyNode setParent(Object parent);
	@Override HierarchyNode addChild(Object... child);
	
	HierarchyNode setData(Object data);
	HierarchyNodeData getData();
	
	HierarchyNode setIsCollapsible(Boolean isCollapsible);
	Boolean getIsCollapsible();
	
	HierarchyNode setIcon(Object icon);
	Object getIcon();
	
	HierarchyNode setFamily(Object family);
	Object getFamily();
	
	Objects getFamilies();
	
	HierarchyNode addNode(Object data);
	
	@Override HierarchyNode getParent();
	@Override HierarchyNode getLastChild();
}
