package org.cyk.utility.client.controller.event;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.programming.script.Script;

public interface Event extends Objectable {

	EventName getName();
	Event setName(EventName name);
	
	Script getScript();
	Event setScript(Script script);
	
}