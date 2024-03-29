package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Components extends CollectionInstance<Component> {
	
	Layout getLayout();
	Components setLayout(Layout layout);

	Components setInputOutputValueFromFieldValue();
	Components setFieldValueFromValue(Class<?>...classes);
	
	Collection<Commandable> getCommandables(Boolean isRecursive);
	Collection<Commandable> getCommandables();
	Commandable getCommandableByIdentifier(Object identifier);
	Commandable getCommandableByIdentifier(Object identifier,Boolean isRecursive);
}
