package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
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
		if(CollectionHelper.isNotEmpty(items)) {
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
		if(items == null && Boolean.TRUE.equals(injectIfNull))
			items = __inject__(MenuItemBuilders.class);
		return items;
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

}
