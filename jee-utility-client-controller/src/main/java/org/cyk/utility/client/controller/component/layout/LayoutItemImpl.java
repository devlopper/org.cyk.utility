package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class LayoutItemImpl extends AbstractVisibleComponentImpl implements LayoutItem,Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	public Layout getLayout() {
		return layout;
	}
	
	@Override
	public LayoutItem setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}
	
	@Override
	public Layout getParent() {
		return (Layout) super.getParent();
	}
	
}
