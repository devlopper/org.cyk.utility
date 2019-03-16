package org.cyk.utility.client.controller.event;

import org.cyk.utility.collection.CollectionInstance;

public interface EventBuilders extends CollectionInstance<EventBuilder> {

	EventBuilder getByName(EventName name, Boolean injectIfNull);
	EventBuilder getByName(EventName name);
}
