package org.cyk.utility.client.controller.component.tree;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class TreeImpl extends AbstractVisibleComponentImpl implements Tree,Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private Object runtimeSelection;
	
	@Override
	public TreeNode getRoot() {
		return root;
	}

	@Override
	public Tree setRoot(TreeNode root) {
		this.root = root;
		return this;
	}

	@Override
	public Object getRuntimeSelection() {
		return runtimeSelection;
	}

	@Override
	public void setRuntimeSelection(Object runtimeSelection) {
		this.runtimeSelection = runtimeSelection;
	}
	
}
