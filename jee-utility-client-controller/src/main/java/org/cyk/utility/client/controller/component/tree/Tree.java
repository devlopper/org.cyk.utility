package org.cyk.utility.client.controller.component.tree;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.__kernel__.object.Objects;

public interface Tree extends VisibleComponent {

	TreeNode getRoot();
	Tree setRoot(TreeNode root);
	
	Object getRuntimeSelection();
	void setRuntimeSelection(Object runtimeSelection);
	
	Menu getMenu();
	Tree setMenu(Menu menu);
	
	Objects getNodeFamilies();
	Objects getNodeFamilies(Boolean injectIfNull);
	Tree setNodeFamilies(Objects nodeFamilies);
	
	TreeRenderType getRenderType();
	Tree setRenderType(TreeRenderType renderType);
	
	Commandable getAddNodeCommandable();
	Tree setAddNodeCommandable(Commandable addNodeCommandable);
	
	Commandable getRemoveNodeCommandable();
	Tree setRemoveNodeCommandable(Commandable removeNodeCommandable);
	
	Tree addData(Object data);
	Tree removeData(Object data);
	Tree removeData();
	
	<T> T getSelectedNodeAs(Class<T> aClass);
	Object getSelectedNodeDataValue();
}
