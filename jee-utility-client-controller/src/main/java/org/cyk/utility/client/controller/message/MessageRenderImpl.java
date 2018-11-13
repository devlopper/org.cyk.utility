package org.cyk.utility.client.controller.message;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.object.Objects;

public class MessageRenderImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements MessageRender,Serializable {
	private static final long serialVersionUID = 1L;

	private Objects messages;
	
	@Override
	public Objects getMessages() {
		return messages;
	}

	@Override
	public MessageRender setMessages(Objects messages) {
		this.messages = messages;
		return this;
	}
	
	@Override
	public Objects getMessages(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_MESSAGES, injectIfNull);
	}
	
	@Override
	public MessageRender addMessages(Collection<Object> messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public MessageRender addMessages(Object... messages) {
		getMessages(Boolean.TRUE).add(messages);
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
	
	public static final String FIELD_MESSAGES = "messages";
}
