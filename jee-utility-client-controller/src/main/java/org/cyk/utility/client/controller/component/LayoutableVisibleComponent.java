package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.layout.LayoutItem;

public interface LayoutableVisibleComponent extends VisibleComponent {

	LayoutItem getLayoutItem();
	LayoutableVisibleComponent setLayoutItem(LayoutItem layoutItem);
	
}
