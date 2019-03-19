package org.cyk.utility.client.controller.event;

import org.cyk.utility.collection.CollectionInstance;

public interface Events extends CollectionInstance<Event> {

	Event getByName(EventName name, Boolean injectIfNull);
	Event getByName(EventName name);
	
}
