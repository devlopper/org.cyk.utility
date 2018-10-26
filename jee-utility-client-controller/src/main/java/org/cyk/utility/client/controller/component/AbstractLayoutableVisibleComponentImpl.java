package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.layout.LayoutItem;

public abstract class AbstractLayoutableVisibleComponentImpl extends AbstractVisibleComponentImpl implements LayoutableVisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;
	
	private LayoutItem layoutItem;
	
	@Override
	public LayoutItem getLayoutItem() {
		return layoutItem;
	}
	
	@Override
	public LayoutableVisibleComponent setLayoutItem(LayoutItem layoutItem) {
		this.layoutItem = layoutItem;
		return this;
	}
	
}
