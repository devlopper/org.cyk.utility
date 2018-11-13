package org.cyk.utility.client.controller.message;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.object.Objects;

public interface MessageRender extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Objects getMessages();
	Objects getMessages(Boolean injectIfNull);
	MessageRender setMessages(Objects messages);
	MessageRender addMessages(Collection<Object> messages);
	MessageRender addMessages(Object...messages);
	
	MessageRenderType getType();
	MessageRender setType(MessageRenderType type);
}
