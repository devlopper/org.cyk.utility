package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.client.controller.component.InvisibleComponent;

public interface LayoutItem extends InvisibleComponent {

	Layout getLayout();
	LayoutItem setLayout(Layout layout);
	
	@Override Layout getParent();
}
