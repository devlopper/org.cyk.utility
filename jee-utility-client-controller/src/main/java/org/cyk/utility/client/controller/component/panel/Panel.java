package org.cyk.utility.client.controller.component.panel;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public interface Panel extends VisibleComponent {

	LayoutItem getHeaderLayoutCellComponent();
	Panel setHeaderLayoutCellComponent(LayoutItem headerLayoutCellComponent);
	
	LayoutItem getBodyLayoutCellComponent();
	Panel setBodyLayoutCellComponent(LayoutItem bodyLayoutCellComponent);
	
	LayoutItem getFooterLayoutCellComponent();
	Panel setFooterLayoutCellComponent(LayoutItem footerLayoutCellComponent);
}
