package org.cyk.utility.__kernel__.user.interface_.message;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;

public abstract class AbstractMessageRendererImpl extends AbstractObject implements MessageRenderer,Serializable {

	@Override
	public void render(Collection<Message> messages, Collection<RenderType> renderTypes) {
		if(CollectionHelper.isEmpty(messages) || CollectionHelper.isEmpty(renderTypes))
			return;
		__render__(messages, renderTypes);
	}
	
	protected abstract void __render__(Collection<Message> messages, Collection<RenderType> renderTypes);
}
