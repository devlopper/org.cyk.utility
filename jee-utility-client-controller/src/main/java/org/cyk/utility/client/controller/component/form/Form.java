package org.cyk.utility.client.controller.component.form;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public interface Form extends VisibleComponent {

	LayoutItem getHeaderLayoutCellComponent();
	Form setHeaderLayoutCellComponent(LayoutItem headerLayoutCellComponent);
	
	LayoutItem getBodyLayoutCellComponent();
	Form setBodyLayoutCellComponent(LayoutItem bodyLayoutCellComponent);
	
	LayoutItem getFooterLayoutCellComponent();
	Form setFooterLayoutCellComponent(LayoutItem footerLayoutCellComponent);
}
