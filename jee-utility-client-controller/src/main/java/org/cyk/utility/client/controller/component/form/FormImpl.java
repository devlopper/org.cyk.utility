package org.cyk.utility.client.controller.component.form;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public class FormImpl extends AbstractVisibleComponentImpl implements Form, Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItem headerLayoutCellComponent,bodyLayoutCellComponent,footerLayoutCellComponent;
	
	@Override
	public LayoutItem getHeaderLayoutCellComponent() {
		return headerLayoutCellComponent;
	}

	@Override
	public Form setHeaderLayoutCellComponent(LayoutItem headerLayoutCellComponent) {
		this.headerLayoutCellComponent = headerLayoutCellComponent;
		return this;
	}

	@Override
	public LayoutItem getBodyLayoutCellComponent() {
		return bodyLayoutCellComponent;
	}

	@Override
	public Form setBodyLayoutCellComponent(LayoutItem bodyLayoutCellComponent) {
		this.bodyLayoutCellComponent = bodyLayoutCellComponent;
		return this;
	}

	@Override
	public LayoutItem getFooterLayoutCellComponent() {
		return footerLayoutCellComponent;
	}

	@Override
	public Form setFooterLayoutCellComponent(LayoutItem footerLayoutCellComponent) {
		this.footerLayoutCellComponent = footerLayoutCellComponent;
		return this;
	}

}
