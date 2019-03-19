package org.cyk.utility.client.controller.component.tree;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.cyk.utility.hierarchy.HierarchyNodeData;
import org.cyk.utility.random.RandomHelper;

public class TreeNodeBuilderImpl extends AbstractVisibleComponentBuilderImpl<TreeNode> implements TreeNodeBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private HierarchyNode hierarchyNode;
	
	@Override
	protected void __execute__(TreeNode node) {
		super.__execute__(node);
		HierarchyNode hierarchyNode = getHierarchyNode();
		if(hierarchyNode!=null) {
			HierarchyNodeData data = hierarchyNode.getData();
			if(data!=null) {
				data.getProperties().setRendered(Boolean.TRUE);
				if(data.getProperties().getIcon() == null)
					data.getProperties().setIcon(hierarchyNode.getIcon());
				if(data.getProperties().getIconPos() == null && data.getProperties().getIcon() != null)
					data.getProperties().setIconPos("left");
			}
			node.setData(data);
			node.setFamily((String) hierarchyNode.getProperty(TreeNode.PROPERTY_FAMILY));
			
			node.setIsCollapsible(hierarchyNode.getIsCollapsible());
			node.setIsDraggable((Boolean) hierarchyNode.getProperty(TreeNode.PROPERTY_IS_DRAGGABLE));
			node.setIsDroppable((Boolean) hierarchyNode.getProperty(TreeNode.PROPERTY_IS_DROPPABLE));
			node.setIsExpanded((Boolean) hierarchyNode.getProperty(TreeNode.PROPERTY_IS_EXPANDED));
			node.setIsSelectable((Boolean) hierarchyNode.getProperty(TreeNode.PROPERTY_IS_SELECTABLE));
			
			Collection<Object> children = hierarchyNode.getChildren();
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(children))) {
				for(Object index : children) {
					if(index instanceof HierarchyNode)
						addChild(__inject__(TreeNodeBuilder.class).setHierarchyNode((HierarchyNode) index));
				}
			}
		}
		
		Collection<Object> children = getChildren();
		Boolean isHasChild = __injectCollectionHelper__().isNotEmpty(children);
		if(isHasChild) {
			for(Object index : children) {
				TreeNode child = null;
				if(index instanceof TreeNodeBuilder)
					child = ((TreeNodeBuilder)index).execute().getOutput();
				if(child != null)
					node.addChild(child);
			}
		}
		
		if(node.getIdentifier() == null)
			node.setIdentifier(__inject__(RandomHelper.class).getAlphabetic(10));
		
		if(node.getIsCollapsible() == null)
			node.setIsCollapsible(isHasChild);
		
		if(node.getIsDraggable() == null)
			node.setIsDraggable(Boolean.TRUE);
		
		if(node.getIsDroppable() == null)
			node.setIsDroppable(Boolean.TRUE);
		
		if(node.getIsSelectable() == null)
			node.setIsSelectable(Boolean.TRUE);
		
		if(node.getIsExpanded() == null)
			node.setIsExpanded(isHasChild);
	}
	
	@Override
	public HierarchyNode getHierarchyNode() {
		return hierarchyNode;
	}
	
	@Override
	public HierarchyNode getHierarchyNode(Boolean injectIfNull) {
		return (HierarchyNode) __getInjectIfNull__(FIELD_HIERARCHY_NODE, injectIfNull);
	}

	@Override
	public TreeNodeBuilder setHierarchyNode(HierarchyNode hierarchyNode) {
		this.hierarchyNode = hierarchyNode;
		return this;
	}
	
	@Override
	public TreeNodeBuilder setHierarchyNodeProperty(Object key, Object value) {
		getHierarchyNode(Boolean.TRUE).setProperty(key, value);
		return this;
	}
	
	@Override
	public String getFamily() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : (String)hierarchyNode.getProperty(TreeNode.PROPERTY_FAMILY);
	}

	@Override
	public TreeNodeBuilder setFamily(String family) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_FAMILY, family);
		return this;
	}

	@Override
	public Object getData() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : hierarchyNode.getProperty(TreeNode.PROPERTY_DATA);
	}

	@Override
	public TreeNodeBuilder setData(Object data) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_DATA, data);
		return this;
	}

	@Override
	public Boolean getIsCollapsible() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : (Boolean)hierarchyNode.getProperty(TreeNode.PROPERTY_IS_COLLAPSIBLE);
	}

	@Override
	public TreeNodeBuilder setIsCollapsible(Boolean isCollapsible) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_IS_COLLAPSIBLE, isCollapsible);
		return this;
	}

	@Override
	public Boolean getIsDroppable() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : (Boolean)hierarchyNode.getProperty(TreeNode.PROPERTY_IS_DROPPABLE);
	}

	@Override
	public TreeNodeBuilder setIsDroppable(Boolean isDroppable) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_IS_DROPPABLE, isDroppable);
		return this;
	}

	@Override
	public Boolean getIsDraggable() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : (Boolean)hierarchyNode.getProperty(TreeNode.PROPERTY_IS_DRAGGABLE);
	}

	@Override
	public TreeNodeBuilder setIsDraggable(Boolean isDraggable) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_IS_DRAGGABLE, isDraggable);
		return this;
	}

	@Override
	public Boolean getIsSelectable() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : (Boolean)hierarchyNode.getProperty(TreeNode.PROPERTY_IS_SELECTABLE);
	}

	@Override
	public TreeNodeBuilder setIsSelectable(Boolean isSelectable) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_IS_SELECTABLE, isSelectable);
		return this;
	}

	@Override
	public Boolean getIsExpanded() {
		HierarchyNode hierarchyNode = getHierarchyNode();
		return hierarchyNode == null ? null : (Boolean)hierarchyNode.getProperty(TreeNode.PROPERTY_IS_EXPANDED);
	}

	@Override
	public TreeNodeBuilder setIsExpanded(Boolean isExpanded) {
		getHierarchyNode(Boolean.TRUE).setProperty(TreeNode.PROPERTY_IS_EXPANDED, isExpanded);
		return this;
	}
	
	@Override
	public TreeNodeBuilder addNode(Object data) {
		addChild(__inject__(TreeNodeBuilder.class).setData(data));
		return this;
	}
	
	/**/
	
	private static final String FIELD_HIERARCHY_NODE = "hierarchyNode";

}
