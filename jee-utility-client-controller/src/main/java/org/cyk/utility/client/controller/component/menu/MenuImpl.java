package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class MenuImpl extends AbstractVisibleComponentImpl implements Menu,Serializable {
	private static final long serialVersionUID = 1L;

	private MenuItems items;
	private MenuRenderType renderType;
	
	@Override
	public MenuItems getItems() {
		return items;
	}
	
	@Override
	public Menu setItems(MenuItems items) {
		this.items = items;
		return this;
	}
	
	@Override
	public MenuRenderType getRenderType() {
		return renderType;
	}

	@Override
	public Menu setRenderType(MenuRenderType renderType) {
		this.renderType = renderType;
		return this;
	}

}
