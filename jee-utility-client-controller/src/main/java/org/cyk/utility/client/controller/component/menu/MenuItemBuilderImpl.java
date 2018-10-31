package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public class MenuItemBuilderImpl extends AbstractVisibleComponentBuilderImpl<MenuItem> implements MenuItemBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@Override
	protected void __execute__(MenuItem menuItem) {
		super.__execute__(menuItem);
		String name = getName();
		menuItem.setName(name);
		Collection<Object> children = getChildren();
		if(__injectCollectionHelper__().isNotEmpty(children)) {
			for(Object index : children) {
				if(index instanceof MenuItemBuilder)
					menuItem.addChild( ((MenuItemBuilder)index).execute().getOutput() );
			}
		}
	}
	
	@Override
	public MenuItemBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public MenuItemBuilder addChild(Object... child) {
		return (MenuItemBuilder) super.addChild(child);
	}
	
	@Override
	public MenuItemBuilder setParent(Object parent) {
		return (MenuItemBuilder) super.setParent(parent);
	}
	
	@Override
	public MenuItemBuilder getParent() {
		return (MenuItemBuilder) super.getParent();
	}
	
}
