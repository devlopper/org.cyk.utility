package org.cyk.utility.hierarchy;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.object.Objects;

@Dependent
public class HierarchyNodeImpl extends AbstractObject implements HierarchyNode,Serializable {
	private static final long serialVersionUID = 1L;

	private HierarchyNodeData data;
	private Boolean isCollapsible;
	private Object icon,family;
	
	@Override
	public HierarchyNode setParent(Object parent) {
		return (HierarchyNode) super.setParent(parent);
	}
	
	@Override
	public HierarchyNode addChild(Object... children) {
		return (HierarchyNode) super.addChild(children);
	}

	@Override
	public HierarchyNode setData(Object data) {
		if(data!=null && !(data instanceof HierarchyNodeData))
			data = __inject__(HierarchyNodeData.class).setValue(data);
		this.data = (HierarchyNodeData) data;
		return this;
	}

	@Override
	public HierarchyNodeData getData() {
		return data;
	}
	
	@Override
	public HierarchyNode addNode(Object data) {
		addChild(__inject__(HierarchyNode.class).setData(data));
		return this;
	}
	
	@Override
	public HierarchyNode getLastChild() {
		return (HierarchyNode) super.getLastChild();
	}
	
	@Override
	public HierarchyNode getParent() {
		return (HierarchyNode) super.getParent();
	}

	@Override
	public HierarchyNode setIsCollapsible(Boolean isCollapsible) {
		this.isCollapsible = isCollapsible;
		return this;
	}

	@Override
	public Boolean getIsCollapsible() {
		return isCollapsible;
	}

	@Override
	public HierarchyNode setIcon(Object icon) {
		this.icon = icon;
		return this;
	}

	@Override
	public Object getIcon() {
		return icon;
	}
	
	@Override
	public Object getFamily() {
		return family;
	}
	
	@Override
	public HierarchyNode setFamily(Object family) {
		this.family = family;
		return this;
	}
	
	@Override
	public Objects getFamilies() {
		Objects objects = __inject__(Objects.class);
		objects.add(getFamily());
		Collection<HierarchyNode> children = getChildrenInstanceOf(HierarchyNode.class);
		if(CollectionHelper.isNotEmpty(children)) {
			for(HierarchyNode index : children)
				objects.add(index.getFamilies());
		}
		return objects;
	}
}
