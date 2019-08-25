package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;

public class TreeNode extends org.primefaces.model.DefaultTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long __numberOfChildren__;
	
	public TreeNode(String type, Object data, org.primefaces.model.TreeNode parent) {
		super(data, parent);
		if(type != null)
			setType(type);
		if(data instanceof DataIdentifiedByString) {
			DataIdentifiedByString<?> node = (DataIdentifiedByString<?>) data;
			__numberOfChildren__ = node.getNumberOfChildren();
		}
		
		if(__numberOfChildren__ == null)
			__numberOfChildren__ = 0l;
		
		if(data instanceof AbstractObject) {
			((AbstractObject)data).getProperties().setTreeNode(this);
		}
	}

	public TreeNode incrementNumberOfChildren() {
		if(__numberOfChildren__ == null)
			__numberOfChildren__ = 0l;
		__numberOfChildren__ = __numberOfChildren__ + 1;
		return this;
	}
	
	public TreeNode decrementNumberOfChildren() {
		if(__numberOfChildren__ == null)
			__numberOfChildren__ = 0l;
		__numberOfChildren__ = __numberOfChildren__ - 1;
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
	
	public TreeNode removeFromParent() {
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
			if(parent.isLeaf())
				index = parent;
			else
				break;
		} while(true);
		return this;
	}
	
	@Override
	public boolean isLeaf() {
		return __numberOfChildren__ == 0;
	}
	
	/**/
	
	
}
