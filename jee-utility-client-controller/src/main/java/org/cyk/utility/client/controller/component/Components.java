package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.collection.CollectionInstance;

public interface Components extends CollectionInstance<Component> {
	
	Layout getLayout();
	Components setLayout(Layout layout);

	Components setInputOutputValueFromFieldValue();
	Components setInputOutputFieldValueFromValue();
}
