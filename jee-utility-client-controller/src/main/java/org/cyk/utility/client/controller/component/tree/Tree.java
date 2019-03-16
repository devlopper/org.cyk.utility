package org.cyk.utility.client.controller.component.tree;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Tree extends VisibleComponent {

	TreeNode getRoot();
	Tree setRoot(TreeNode root);
	
	Object getRuntimeSelection();
	void setRuntimeSelection(Object runtimeSelection);
	
}
