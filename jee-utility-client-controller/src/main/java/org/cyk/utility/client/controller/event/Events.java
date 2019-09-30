package org.cyk.utility.client.controller.event;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Events extends CollectionInstance<Event> {

	Event getByName(EventName name, Boolean injectIfNull);
	Event getByName(EventName name);
	
}
