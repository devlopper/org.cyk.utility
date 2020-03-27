package org.cyk.utility.__kernel__.user.interface_.message;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.annotation.Test;

@Test
public class MessageRendererTestImpl extends MessageRenderer.AbstractImpl implements Serializable {

	@Override
	public MessageRenderer clear() {
		return this;
	}

	@Override
	protected void __render__(Collection<Message> messages, Collection<RenderType> renderTypes) {
		messages.forEach(message -> {
			System.out.println(message);
		});
	}

}
