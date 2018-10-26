package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface LayoutItem extends VisibleComponent {

	Layout getLayout();
	LayoutItem setLayout(Layout layout);
	
	@Override Layout getParent();
}
