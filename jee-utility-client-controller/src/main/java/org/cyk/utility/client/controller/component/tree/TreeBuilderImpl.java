package org.cyk.utility.client.controller.component.tree;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnContext;

public class TreeBuilderImpl extends AbstractVisibleComponentBuilderImpl<Tree> implements TreeBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNodeBuilder root;
	private MenuBuilder menu;
	private TreeRenderType renderType;
	private Objects nodeFamilies,defaultNodeFamilies;
	private CommandableBuilder addNodeCommandable,removeNodeCommandable;
	
	@Override
	protected void __execute__(Tree tree) {
		super.__execute__(tree);
		TreeRenderType renderType = getRenderType();
		if(renderType == null)
			renderType = __inject__(TreeRenderTypeDefault.class);
		tree.setRenderType(renderType);
		TreeNodeBuilder root = getRoot();
		if(root!=null) {
			tree.setRoot(__inject__(TreeNode.class).setHierarchyNode(root.getHierarchyNode()));
		}
		if(tree.getNodeFamilies() == null) {
			if(root!=null && root.getHierarchyNode()!=null) {
				tree.setNodeFamilies(root.getHierarchyNode().getFamilies());
			}
		}
		
		Objects defaultNodeFamilies = getDefaultNodeFamilies();
		if(CollectionHelper.isNotEmpty(defaultNodeFamilies)) {
			tree.getNodeFamilies(Boolean.TRUE).add(defaultNodeFamilies);
		}
		
		CommandableBuilder addNodeCommandable = getAddNodeCommandable();
		
		CommandableBuilder removeNodeCommandable = getRemoveNodeCommandable();
		
		MenuBuilder menu = getMenu();
		if(menu!=null) {
			if(menu.getRenderType() == null)
				menu.setRenderType(__inject__(MenuRenderTypeColumnContext.class));
			if(menu.getLinkedTo() == null)
				menu.setLinkedTo(tree);
			tree.setMenu(menu.execute().getOutput());
		}
		
		if(addNodeCommandable!=null)
			tree.setAddNodeCommandable(addNodeCommandable.getComponent());
		
		if(removeNodeCommandable!=null)
			tree.setRemoveNodeCommandable(removeNodeCommandable.getComponent());
	}
	
	@Override
	public TreeNodeBuilder getRoot() {
		return root;
	}
	
	@Override
	public TreeNodeBuilder getRoot(Boolean injectIfNull) {
		if(root == null && Boolean.TRUE.equals(injectIfNull))
			root = __inject__(TreeNodeBuilder.class);
		return root;
	}
	
	@Override
	public TreeBuilder setRoot(TreeNodeBuilder root) {
		this.root = root;
		return this;
	}
	
	@Override
	public MenuBuilder getMenu() {
		return menu;
	}
	
	@Override
	public MenuBuilder getMenu(Boolean injectIfNull) {
		if(menu == null && Boolean.TRUE.equals(injectIfNull))
			menu = __inject__(MenuBuilder.class);
		return menu;
	}
	
	@Override
	public TreeBuilder setMenu(MenuBuilder menu) {
		this.menu = menu;
		return this;
	}
	
	@Override
	public TreeRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public TreeBuilder setRenderType(TreeRenderType renderType) {
		this.renderType = renderType;
		return this;
	}
	
	@Override
	public Objects getNodeFamilies() {
		return nodeFamilies;
	}

	@Override
	public Objects getNodeFamilies(Boolean injectIfNull) {
		if(nodeFamilies == null && Boolean.TRUE.equals(injectIfNull))
			nodeFamilies = __inject__(Objects.class);
		return nodeFamilies;
	}

	@Override
	public TreeBuilder setNodeFamilies(Objects nodeFamilies) {
		this.nodeFamilies = nodeFamilies;
		return this;
	}
	
	@Override
	public Objects getDefaultNodeFamilies() {
		return defaultNodeFamilies;
	}

	@Override
	public Objects getDefaultNodeFamilies(Boolean injectIfNull) {
		if(defaultNodeFamilies == null && Boolean.TRUE.equals(injectIfNull))
			defaultNodeFamilies = __inject__(Objects.class);
		return defaultNodeFamilies;
	}

	@Override
	public TreeBuilder setDefaultNodeFamilies(Objects defaultNodeFamilies) {
		this.defaultNodeFamilies = defaultNodeFamilies;
		return this;
	}
	
	@Override
	public CommandableBuilder getAddNodeCommandable() {
		return addNodeCommandable;
	}

	@Override
	public CommandableBuilder getAddNodeCommandable(Boolean injectIfNull) {
		if(addNodeCommandable == null && Boolean.TRUE.equals(injectIfNull))
			addNodeCommandable = __inject__(CommandableBuilder.class);
		return addNodeCommandable;
	}

	@Override
	public TreeBuilder setAddNodeCommandable(CommandableBuilder addNodeCommandable) {
		this.addNodeCommandable = addNodeCommandable;
		return this;
	}

	@Override
	public CommandableBuilder getRemoveNodeCommandable() {
		return removeNodeCommandable;
	}

	@Override
	public CommandableBuilder getRemoveNodeCommandable(Boolean injectIfNull) {
		if(removeNodeCommandable == null && Boolean.TRUE.equals(injectIfNull))
			removeNodeCommandable = __inject__(CommandableBuilder.class);
		return removeNodeCommandable;
	}

	@Override
	public TreeBuilder setRemoveNodeCommandable(CommandableBuilder removeNodeCommandable) {
		this.removeNodeCommandable = removeNodeCommandable;
		return this;
	}
	
	/**/
	
}
