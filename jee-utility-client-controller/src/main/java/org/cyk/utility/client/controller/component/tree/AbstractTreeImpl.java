package org.cyk.utility.client.controller.component.tree;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.object.Objects;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractTreeImpl extends AbstractVisibleComponentImpl implements Tree,Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private Object runtimeSelection;
	private Menu menu;
	private Objects nodeFamilies;
	private TreeRenderType renderType;
	private Commandable addNodeCommandable,removeNodeCommandable;
	
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
	
	@Override
	public Menu getMenu() {
		return menu;
	}
	
	@Override
	public Tree setMenu(Menu menu) {
		this.menu = menu;
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
	public Tree setNodeFamilies(Objects nodeFamilies) {
		this.nodeFamilies = nodeFamilies;
		return this;
	}
	
	@Override
	public TreeRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public Tree setRenderType(TreeRenderType renderType) {
		this.renderType = renderType;
		return this;
	}
	
	@Override
	public Commandable getAddNodeCommandable() {
		return addNodeCommandable;
	}

	@Override
	public Tree setAddNodeCommandable(Commandable addNodeCommandable) {
		this.addNodeCommandable = addNodeCommandable;
		return this;
	}

	@Override
	public Commandable getRemoveNodeCommandable() {
		return removeNodeCommandable;
	}

	@Override
	public Tree setRemoveNodeCommandable(Commandable removeNodeCommandable) {
		this.removeNodeCommandable = removeNodeCommandable;
		return this;
	}
	
	@Override
	public Tree addData(Object data) {
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
		return null;
	}
	
	@Override
	public Tree removeData(Object data) {
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
		return null;
	}
	
	@Override
	public Tree removeData() {
		return removeData(null);
	}
	
	@Override
	public <T> T getSelectedNodeAs(Class<T> aClass) {
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
		return null;
	}

	@Override
	public Object getSelectedNodeDataValue() {
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
		return null;
	}
	
	/**/
	
}
