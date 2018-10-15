package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.layout.LayoutItem;

public abstract class AbstractVisibleComponentImpl extends AbstractComponentImpl implements VisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItem layoutItem;
	
	@Override
	public LayoutItem getLayoutItem() {
		return layoutItem;
	}
	/*
	@Override
	public LayoutItem getLayoutItem(Boolean injectIfNull) {
		LayoutItem layoutItem = getLayoutItem();
		if(layoutItem == null && Boolean.TRUE.equals(injectIfNull))
			setLayoutItem(layoutItem = __inject__(LayoutItem.class));
		return layoutItem;
	}
	*/
	@Override
	public VisibleComponent setLayoutItem(LayoutItem layoutItem) {
		this.layoutItem = layoutItem;
		return this;
	}
	
}
