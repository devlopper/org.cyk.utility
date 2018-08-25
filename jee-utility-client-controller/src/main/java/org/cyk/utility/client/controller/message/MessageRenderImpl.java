package org.cyk.utility.client.controller.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class MessageRenderImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements MessageRender,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getMessages() {
		return (Collection<Object>) getProperties().getMessage();
	}

	@Override
	public MessageRender setMessages(Collection<Object> messages) {
		getProperties().setMessage(messages);
		return this;
	}

	@Override
	public MessageRenderType getType() {
		return (MessageRenderType) getProperties().getType();
	}

	@Override
	public MessageRender setType(MessageRenderType type) {
		getProperties().setType(type);
		return this;
	}

	@Override
	public MessageRender addOneMessage(Object message) {
		return addManyMessages(message);
	}
	
	@Override
	public MessageRender addManyMessages(Object... messages) {
		if(__inject__(ArrayHelper.class).isNotEmpty(messages)) {
			Collection<Object> collection = getMessages();
			if(collection == null)
				setMessages(collection = new ArrayList<>());	
			__injectCollectionHelper__().add(collection, messages);
		}
		
		return this;
	}
}
