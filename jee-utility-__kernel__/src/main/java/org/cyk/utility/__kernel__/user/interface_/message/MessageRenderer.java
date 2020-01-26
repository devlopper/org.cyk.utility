package org.cyk.utility.__kernel__.user.interface_.message;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MessageRenderer {

	void render(Collection<Message> messages,Collection<RenderType> renderTypes);
	
	default void render(Message message,Collection<RenderType> renderTypes) {
		if(message == null)
			return;
		render(CollectionHelper.listOf(message), renderTypes);
	}
	
	default void render(Message message,RenderType...renderTypes) {
		if(ArrayHelper.isEmpty(renderTypes))
			renderTypes = new RenderType[] {RenderType.DIALOG};
		render(message, CollectionHelper.listOf(renderTypes));
	}
	
	/**/
	
	static MessageRenderer getInstance() {
		return Helper.getInstance(MessageRenderer.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}