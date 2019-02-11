package org.cyk.utility.network.message;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;

public class MessageImpl extends AbstractObject implements Message , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getTitle() {
		return (String) __inject__(CollectionHelper.class).getFirst(getPropertiesWhereKeyIsInstanceOf(MessagePropertyTitle.class));
	}
	
	@Override
	public Message setTitle(String title) {
		MessagePropertyTitle key = __inject__(MessagePropertyTitle.class);
		getProperties().set(key, title);
		return this;
	}
	
	@Override
	public String getBody() {
		return (String) __inject__(CollectionHelper.class).getFirst(getPropertiesWhereKeyIsInstanceOf(MessagePropertyBody.class));
	}
	
	@Override
	public Message setBody(String body) {
		MessagePropertyBody key = __inject__(MessagePropertyBody.class);
		getProperties().set(key, body);
		return this;
	}
}
