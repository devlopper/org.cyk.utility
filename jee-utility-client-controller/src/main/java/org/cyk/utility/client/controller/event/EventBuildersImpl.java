package org.cyk.utility.client.controller.event;

import java.io.Serializable;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;

public class EventBuildersImpl extends AbstractCollectionInstanceImpl<EventBuilder> implements EventBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public EventBuilder getByName(EventName name, Boolean injectIfNull) {
		EventBuilder builder = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(collection)) {
			for(EventBuilder index : collection)
				if(name.equals(index.getName())) {
					builder= index;
					break;
				}
		}
		if(builder == null && Boolean.TRUE.equals(injectIfNull))
			builder = __inject__(EventBuilder.class).setName(name);
		return builder;
	}

	@Override
	public EventBuilder getByName(EventName name) {
		return getByName(name, null);
	}

}
