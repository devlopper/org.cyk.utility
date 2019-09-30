package org.cyk.utility.client.controller.event;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

public class EventsImpl extends AbstractCollectionInstanceImpl<Event> implements Events,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Event getByName(EventName name, Boolean injectIfNull) {
		Event event = null;
		if(CollectionHelper.isNotEmpty(collection)) {
			for(Event index : collection)
				if(name.equals(index.getName())) {
					event = index;
					break;
				}
		}
		if(event == null && Boolean.TRUE.equals(injectIfNull))
			event = __inject__(Event.class).setName(name);
		return event;
	}

	@Override
	public Event getByName(EventName name) {
		return getByName(name, null);
	}
	
}
