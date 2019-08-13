package org.cyk.utility.client.controller.data;

import org.cyk.utility.__kernel__.object.__static__.representation.Action;
import org.cyk.utility.__kernel__.object.__static__.representation.Actions;
import org.cyk.utility.client.controller.Objectable;

public interface Data extends Objectable {
	
	Actions get__actions__();
	Data set__actions__(Actions __actions__);
	
	Action getActionByIdentifier(String identifier);
	
	String get__actionUniformResourceLocatorByIdentifier__(String identifier);
	
}
