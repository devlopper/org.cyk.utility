package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.layout.LayoutItem;

public interface VisibleComponent extends Component {

	LayoutItem getLayoutCellComponent();
	LayoutItem getLayoutCellComponent(Boolean injectIfNull);
	VisibleComponent setLayoutCellComponent(LayoutItem layoutCellComponent);
	
}
