package org.cyk.utility.client.controller.component.form;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.layout.LayoutCell;

public interface Form extends VisibleComponent {

	LayoutCell getHeaderLayoutCellComponent();
	Form setHeaderLayoutCellComponent(LayoutCell headerLayoutCellComponent);
	
	LayoutCell getBodyLayoutCellComponent();
	Form setBodyLayoutCellComponent(LayoutCell bodyLayoutCellComponent);
	
	LayoutCell getFooterLayoutCellComponent();
	Form setFooterLayoutCellComponent(LayoutCell footerLayoutCellComponent);
}
