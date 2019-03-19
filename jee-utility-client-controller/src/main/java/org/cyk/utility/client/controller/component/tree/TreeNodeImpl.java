package org.cyk.utility.client.controller.component.tree;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.hierarchy.HierarchyNode;

public class TreeNodeImpl extends AbstractVisibleComponentImpl implements TreeNode,Serializable {
	private static final long serialVersionUID = 1L;

	private String family;
	private Object data;
	private Boolean isCollapsible,isDroppable,isDraggable,isSelectable,isExpanded;
	private HierarchyNode hierarchyNode;
	
	@Override
	public TreeNode setParent(Object parent) {
		return (TreeNode) super.setParent(parent);
	}
	
	@Override
	public TreeNode addChild(Object... children) {
		return (TreeNode) super.addChild(children);
	}
	
	@Override
	public TreeNode addChild(String family, Object data) {
		addChild(__inject__(TreeNode.class).setFamily(family).setData(data));
		return this;
	}
	
	@Override
	public HierarchyNode getHierarchyNode() {
		return hierarchyNode;
	}
	
	@Override
	public TreeNode setHierarchyNode(HierarchyNode hierarchyNode) {
		this.hierarchyNode = hierarchyNode;
		return this;
	}
	
	@Override
	public String getFamily() {
		return family;
	}

	@Override
	public TreeNode setFamily(String family) {
		this.family = family;
		return this;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public TreeNode setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public Boolean getIsCollapsible() {
		return isCollapsible;
	}

	@Override
	public TreeNode setIsCollapsible(Boolean isCollapsible) {
		this.isCollapsible = isCollapsible;
		return this;
	}

	@Override
	public Boolean getIsDroppable() {
		return isDroppable;
	}

	@Override
	public TreeNode setIsDroppable(Boolean isDroppable) {
		this.isDroppable = isDroppable;
		return this;
	}

	@Override
	public Boolean getIsDraggable() {
		return isDraggable;
	}

	@Override
	public TreeNode setIsDraggable(Boolean isDraggable) {
		this.isDraggable = isDraggable;
		return this;
	}
	
	@Override
	public Boolean getIsSelectable() {
		return isSelectable;
	}

	@Override
	public TreeNode setIsSelectable(Boolean isSelectable) {
		this.isSelectable = isSelectable;
		return this;
	}

	@Override
	public Boolean getIsExpanded() {
		return isExpanded;
	}

	@Override
	public TreeNode setIsExpanded(Boolean isExpanded) {
		this.isExpanded = isExpanded;
		return this;
	}
}
