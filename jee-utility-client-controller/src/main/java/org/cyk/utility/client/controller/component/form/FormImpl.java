package org.cyk.utility.client.controller.component.form;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.layout.LayoutCell;

public class FormImpl extends AbstractVisibleComponentImpl implements Form, Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutCell headerLayoutCellComponent,bodyLayoutCellComponent,footerLayoutCellComponent;
	
	@Override
	public LayoutCell getHeaderLayoutCellComponent() {
		return headerLayoutCellComponent;
	}

	@Override
	public Form setHeaderLayoutCellComponent(LayoutCell headerLayoutCellComponent) {
		this.headerLayoutCellComponent = headerLayoutCellComponent;
		return this;
	}

	@Override
	public LayoutCell getBodyLayoutCellComponent() {
		return bodyLayoutCellComponent;
	}

	@Override
	public Form setBodyLayoutCellComponent(LayoutCell bodyLayoutCellComponent) {
		this.bodyLayoutCellComponent = bodyLayoutCellComponent;
		return this;
	}

	@Override
	public LayoutCell getFooterLayoutCellComponent() {
		return footerLayoutCellComponent;
	}

	@Override
	public Form setFooterLayoutCellComponent(LayoutCell footerLayoutCellComponent) {
		this.footerLayoutCellComponent = footerLayoutCellComponent;
		return this;
	}

}
