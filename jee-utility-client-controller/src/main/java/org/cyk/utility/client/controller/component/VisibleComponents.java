package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.collection.CollectionInstance;

@Deprecated
public interface VisibleComponents extends CollectionInstance<VisibleComponent> {

	Layout getLayout();
	VisibleComponents setLayout(Layout layout);
	
}
