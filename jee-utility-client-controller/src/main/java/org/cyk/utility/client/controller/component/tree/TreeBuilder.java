package org.cyk.utility.client.controller.component.tree;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.__kernel__.object.Objects;

public interface TreeBuilder extends VisibleComponentBuilder<Tree> {

	TreeNodeBuilder getRoot();
	TreeNodeBuilder getRoot(Boolean injectIfNull);
	TreeBuilder setRoot(TreeNodeBuilder root);
	
	MenuBuilder getMenu();
	MenuBuilder getMenu(Boolean injectIfNull);
	TreeBuilder setMenu(MenuBuilder menu);
	
	TreeRenderType getRenderType();
	TreeBuilder setRenderType(TreeRenderType renderType);
	
	Objects getNodeFamilies();
	Objects getNodeFamilies(Boolean injectIfNull);
	TreeBuilder setNodeFamilies(Objects nodeFamilies);
	
	Objects getDefaultNodeFamilies();
	Objects getDefaultNodeFamilies(Boolean injectIfNull);
	TreeBuilder setDefaultNodeFamilies(Objects defaultNodeFamilies);
	
	CommandableBuilder getAddNodeCommandable();
	CommandableBuilder getAddNodeCommandable(Boolean injectIfNull);
	TreeBuilder setAddNodeCommandable(CommandableBuilder addNodeCommandable);
	
	CommandableBuilder getRemoveNodeCommandable();
	CommandableBuilder getRemoveNodeCommandable(Boolean injectIfNull);
	TreeBuilder setRemoveNodeCommandable(CommandableBuilder removeNodeCommandable);
}
