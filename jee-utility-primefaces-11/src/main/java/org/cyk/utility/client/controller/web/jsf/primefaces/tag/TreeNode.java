package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TreeNode extends org.primefaces.model.DefaultTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long numberOfChildren;
	
	public TreeNode(String type, Object data,Long numberOfChildren, org.primefaces.model.TreeNode parent,Map<Object,TreeNode> map) {
		super(data, parent);
		if(type != null)
			setType(type);
		if(numberOfChildren == null) {
			if(data instanceof DataIdentifiedByString) {
				DataIdentifiedByString<?> node = (DataIdentifiedByString<?>) data;
				numberOfChildren = node.getNumberOfChildren();
			}	
		}
	
		if(numberOfChildren == null)
			numberOfChildren = 0l;
		
		this.numberOfChildren = numberOfChildren;
		if(map != null)
			map.put(data, this);
	}
	
	public TreeNode(String type, Object data, org.primefaces.model.TreeNode parent,Map<Object,TreeNode> map) {
		this(type,data,null, parent,map);
	}
	
	public TreeNode() {
		this(null,null,null,null);
	}

	public TreeNode incrementNumberOfChildren() {
		if(numberOfChildren == null)
			numberOfChildren = 0l;
		numberOfChildren = numberOfChildren + 1;
		return this;
	}
	
	public TreeNode decrementNumberOfChildren() {
		if(numberOfChildren == null)
			numberOfChildren = 0l;
		numberOfChildren = numberOfChildren - 1;
		return this;
	}
	
	public TreeNode addChildren(TreeNode treeNode) {
		getChildren().add(treeNode);
		incrementNumberOfChildren();
		return this;
	}
	
	public TreeNode removeChildren(TreeNode treeNode) {
		getChildren().remove(treeNode);
		decrementNumberOfChildren();
		return this;
	}
	
	public TreeNode removeFromParent(Boolean isConsiderNumberOfChildren,Boolean isConsiderNumberOfLoadedChildren) {
		TreeNode index = this;
		do {
			if(index.getParent() == null)
				break;
			if(index.getParent().getData() == null) {
				index.getParent().getChildren().remove(index);
				break;
			}
			TreeNode parent = (TreeNode) index.getParent();
			parent.removeChildren(index);
			Boolean isLeaf = null;
			if(Boolean.TRUE.equals(isConsiderNumberOfLoadedChildren))
				isLeaf = parent.getChildren().isEmpty();
			if(Boolean.TRUE.equals(isConsiderNumberOfChildren))
				isLeaf = parent.numberOfChildren == 0;
			
			if(Boolean.TRUE.equals(isLeaf))
				index = parent;
			else
				break;
		} while(true);
		return this;
	}
	
	public TreeNode removeFromParent() {
		return removeFromParent(Boolean.TRUE, Boolean.FALSE);
	}
	
	@Override
	public boolean isLeaf() {
		return numberOfChildren == 0;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> getDatas(Integer isHavingNumberOfChildren,Class<T> klass) {
		return (Collection<T>) DependencyInjection.inject(PrimefacesHelper.class).getDatas(this,isHavingNumberOfChildren);
	}
	
	public Collection<Object> getDatas(Integer isHavingNumberOfChildren) {
		return getDatas(isHavingNumberOfChildren,Object.class);
	}
	
	public TreeNode getChildByData(Object data) {
		for(org.primefaces.model.TreeNode index : getChildren())
			if(data.equals(index.getData()))
				return (TreeNode) index;
		return null;
	}
	
	public Boolean isParentOfOneOrMoreData(Collection<?> datas) {
		Boolean value = null;
		if(CollectionHelper.isNotEmpty(datas)) {
			for(Object index : datas) {
				if(getChildByData(index) != null) {
					value = Boolean.TRUE;
					break;
				}
			}
		}
		return Boolean.TRUE.equals(value);
	}
	
	/**/
	
	
}
