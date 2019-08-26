package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TreeNode extends org.primefaces.model.DefaultTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long numberOfChildren;
	
	public TreeNode(String type, Object data,Long numberOfChildren, org.primefaces.model.TreeNode parent) {
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
		
		if(data instanceof AbstractObject) {
			((AbstractObject)data).getProperties().setTreeNode(this);
		}
	}
	
	public TreeNode(String type, Object data, org.primefaces.model.TreeNode parent) {
		this(type,data,null, parent);
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
	
	/**/
	
	
}
