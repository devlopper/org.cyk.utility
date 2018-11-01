package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;

public class MenuItemBuilderImpl extends AbstractVisibleComponentBuilderImpl<MenuItem> implements MenuItemBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private NavigationBuilder navigation;
	
	@Override
	protected void __execute__(MenuItem menuItem) {
		super.__execute__(menuItem);
		String name = getName();
		menuItem.setName(name);
		NavigationBuilder navigation = getNavigation();
		if(navigation!=null)
			menuItem.setNavigation(navigation.execute().getOutput());
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
	public NavigationBuilder getNavigation() {
		return navigation;
	}
	
	@Override
	public NavigationBuilder getNavigation(Boolean injectIfNull) {
		return (NavigationBuilder) __getInjectIfNull__(FIELD_NAVIGATION, injectIfNull);
	}
	
	@Override
	public MenuItemBuilder setNavigation(NavigationBuilder navigation) {
		this.navigation = navigation;
		return this;
	}
	
	@Override
	public MenuItemBuilder setNavigationIdentifier(Object identifier) {
		getNavigation(Boolean.TRUE).setIdentifier(identifier);
		return this;
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
	
	/**/
	
	public static final String FIELD_NAVIGATION = "navigation";
	
}
