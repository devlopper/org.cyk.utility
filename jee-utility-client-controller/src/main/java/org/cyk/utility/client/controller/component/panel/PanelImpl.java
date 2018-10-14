package org.cyk.utility.client.controller.component.panel;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public class PanelImpl extends AbstractVisibleComponentImpl implements Panel, Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItem headerLayoutCellComponent,bodyLayoutCellComponent,footerLayoutCellComponent;
	
	@Override
	public LayoutItem getHeaderLayoutCellComponent() {
		return headerLayoutCellComponent;
	}

	@Override
	public Panel setHeaderLayoutCellComponent(LayoutItem headerLayoutCellComponent) {
		this.headerLayoutCellComponent = headerLayoutCellComponent;
		return this;
	}

	@Override
	public LayoutItem getBodyLayoutCellComponent() {
		return bodyLayoutCellComponent;
	}

	@Override
	public Panel setBodyLayoutCellComponent(LayoutItem bodyLayoutCellComponent) {
		this.bodyLayoutCellComponent = bodyLayoutCellComponent;
		return this;
	}

	@Override
	public LayoutItem getFooterLayoutCellComponent() {
		return footerLayoutCellComponent;
	}

	@Override
	public Panel setFooterLayoutCellComponent(LayoutItem footerLayoutCellComponent) {
		this.footerLayoutCellComponent = footerLayoutCellComponent;
		return this;
	}

}
