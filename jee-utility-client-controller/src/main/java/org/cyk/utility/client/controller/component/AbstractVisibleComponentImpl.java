package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.layout.LayoutCell;
import org.cyk.utility.client.controller.component.layout.LayoutCellResponsive;

public abstract class AbstractVisibleComponentImpl extends AbstractComponentImpl implements VisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutCell layoutCellComponent;
	
	@Override
	public LayoutCell getLayoutCellComponent() {
		return layoutCellComponent;
	}
	
	@Override
	public LayoutCell getLayoutCellComponent(Boolean injectIfNull) {
		LayoutCell layoutCell = getLayoutCellComponent();
		if(layoutCell == null && Boolean.TRUE.equals(injectIfNull))
			setLayoutCellComponent(layoutCell = __inject__(LayoutCellResponsive.class));
		return layoutCell;
	}
	
	@Override
	public VisibleComponent setLayoutCellComponent(LayoutCell layoutCellComponent) {
		this.layoutCellComponent = layoutCellComponent;
		return this;
	}
	
}
