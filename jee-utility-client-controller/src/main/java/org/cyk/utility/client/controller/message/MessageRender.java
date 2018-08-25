package org.cyk.utility.client.controller.message;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface MessageRender extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Collection<Object> getMessages();
	MessageRender setMessages(Collection<Object> messages);
	MessageRender addOneMessage(Object message);
	MessageRender addManyMessages(Object...messages);
	
	MessageRenderType getType();
	MessageRender setType(MessageRenderType type);
}
