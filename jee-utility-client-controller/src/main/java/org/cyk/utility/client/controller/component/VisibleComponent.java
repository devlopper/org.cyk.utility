package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.layout.LayoutItem;

public interface VisibleComponent extends Component {

	LayoutItem getLayoutItem();
	//LayoutItem getLayoutItem(Boolean injectIfNull);
	VisibleComponent setLayoutItem(LayoutItem layoutItem);
		
}
