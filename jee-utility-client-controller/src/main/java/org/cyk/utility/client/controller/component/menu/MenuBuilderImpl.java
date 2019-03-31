package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public class MenuBuilderImpl extends AbstractVisibleComponentBuilderImpl<Menu> implements MenuBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private MenuItemBuilders items;
	private MenuRenderType renderType;
	
	//TODO improve build logic to reduce build time
	@Override
	protected void __execute__(Menu menu) {
		super.__execute__(menu);
		MenuItemBuilders items = getItems();
		if(__injectCollectionHelper__().isNotEmpty(items)) {
			menu.setItems(__inject__(MenuItems.class));
			for(MenuItemBuilder index : items.get()) {
				if(index.getRequest() == null)
					index.setRequest(getRequest());
				menu.getItems().add(index.execute().getOutput());
			}
		}
		MenuRenderType renderType = getRenderType();
		menu.setRenderType(renderType);
	}
	
	@Override
	public MenuItemBuilders getItems() {
		return items;
	}
	
	@Override
	public MenuItemBuilders getItems(Boolean injectIfNull) {
		return (MenuItemBuilders) __getInjectIfNull__(FIELD_ITEMS, injectIfNull);
	}
	
	@Override
	public MenuBuilder setItems(MenuItemBuilders items) {
		this.items = items;
		return this;
	}
	
	@Override
	public MenuBuilder addItems(Collection<MenuItemBuilder> items) {
		getItems(Boolean.TRUE).add(items);
		return this;
	}
	
	@Override
	public MenuBuilder addItems(MenuItemBuilder... items) {
		getItems(Boolean.TRUE).add(items);
		return this;
	}
	
	@Override
	public MenuRenderType getRenderType() {
		return renderType;
	}

	@Override
	public MenuBuilder setRenderType(MenuRenderType renderType) {
		this.renderType = renderType;
		return this;
	}

	/**/
	
	public static final String FIELD_ITEMS = "items";
}
