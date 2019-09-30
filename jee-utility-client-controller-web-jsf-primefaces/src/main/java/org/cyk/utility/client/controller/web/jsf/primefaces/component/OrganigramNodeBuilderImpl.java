package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.component.tree.TreeNode;
import org.cyk.utility.client.controller.component.tree.TreeNodeBuilder;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

public class OrganigramNodeBuilderImpl extends AbstractComponentBuilderImpl<OrganigramNode, TreeNode> implements OrganigramNodeBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private HierarchyNode hierarchyNode;
	
	@Override
	protected OrganigramNode __execute__(TreeNode model, ValueExpressionMap valueExpressionMap) throws Exception {
		DefaultOrganigramNode node = new DefaultOrganigramNode();
		OrganigramNode parent = getParentAs(OrganigramNode.class);
		node.setParent(parent);
		Object data = model.getData();
		node.setData(data);
		String family = model.getFamily();
		if(StringHelper.isNotBlank(family))
			node.setType(family);
		node.setCollapsible(Boolean.TRUE.equals(model.getIsCollapsible()));
		node.setDraggable(Boolean.TRUE.equals(model.getIsDraggable()));
		node.setDroppable(Boolean.TRUE.equals(model.getIsDroppable()));
		node.setSelectable(Boolean.TRUE.equals(model.getIsSelectable()));
		node.setExpanded(Boolean.TRUE.equals(model.getIsExpanded()));
		Collection<Object> children = model.getChildren();
		if(CollectionHelper.isNotEmpty(children)) {
			for(Object index : children) {
				TreeNode indexTreeNode = null;
				if(index instanceof TreeNode)
					indexTreeNode = (TreeNode) index;
				else if(index instanceof TreeNodeBuilder)
					indexTreeNode = ((TreeNodeBuilder)index).execute().getOutput();
				
				if(indexTreeNode!=null)
					__inject__(OrganigramNodeBuilder.class).setParent(node).setModel(indexTreeNode).execute();
			}
		}
		return node;
	}
	
	@Override
	protected TreeNode __executeGetModel__(TreeNode model) {
		if(model == null) {
			HierarchyNode hierarchyNode = getHierarchyNode();	
			if(hierarchyNode!=null)
				model = __inject__(TreeNodeBuilder.class).setHierarchyNode(hierarchyNode).execute().getOutput();
		}
		
		return model;
	}

	@Override
	public OrganigramNodeBuilder setParent(Object parent) {
		return (OrganigramNodeBuilder) super.setParent(parent);
	}
	
	@Override
	public OrganigramNodeBuilder setModel(TreeNode model) {
		return (OrganigramNodeBuilder) super.setModel(model);
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
	public OrganigramNodeBuilder setHierarchyNode(HierarchyNode hierarchyNode) {
		this.hierarchyNode = hierarchyNode;
		return this;
	}
	
	private static final String FIELD_HIERARCHY_NODE = "hierarchyNode";
}
