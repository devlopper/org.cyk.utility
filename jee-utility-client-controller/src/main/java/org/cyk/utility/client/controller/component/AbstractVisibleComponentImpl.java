package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.layout.LayoutItem;

public abstract class AbstractVisibleComponentImpl extends AbstractComponentImpl implements VisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItem layoutCellComponent;
	
	@Override
	public LayoutItem getLayoutCellComponent() {
		return layoutCellComponent;
	}
	
	@Override
	public LayoutItem getLayoutCellComponent(Boolean injectIfNull) {
		LayoutItem layoutCell = getLayoutCellComponent();
		if(layoutCell == null && Boolean.TRUE.equals(injectIfNull))
			setLayoutCellComponent(layoutCell = __inject__(LayoutItem.class));
		return layoutCell;
	}
	
	@Override
	public VisibleComponent setLayoutCellComponent(LayoutItem layoutCellComponent) {
		this.layoutCellComponent = layoutCellComponent;
		return this;
	}
	
}
