package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.client.controller.component.InvisibleComponentBuilder;

public interface InsertBuilder extends InvisibleComponentBuilder<Insert> {

	InsertBuilder setName(String name);
	String getName();
	
}
