package org.cyk.utility.client.controller.event;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class EventBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Event> implements EventBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Event __execute__() throws Exception {
		Event event = __inject__(Event.class);
		event.getProperties().copyNonNullKeysFrom(getOutputProperties());
		return event;
	}
	
}
