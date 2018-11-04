package org.cyk.utility.client.controller.component.command;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Commandable extends VisibleComponent {

	String getName();
	Commandable setName(String name);
	
}
