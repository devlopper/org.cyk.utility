package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.client.controller.web.ComponentHelper;

public abstract class AbstractMessageRendererImpl extends org.cyk.utility.__kernel__.user.interface_.message.AbstractMessageRendererImpl implements Serializable {

	@Override
	protected void __render__(Collection<Message> messages, Collection<RenderType> renderTypes) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if(facesContext == null) {
			LogHelper.logSevere("Cannot render message because facesContext is null", getClass());
			return;
		}
		for(Message message : messages) {
			FacesMessage facesMessage = ValueConverter.getInstance().convert(message, FacesMessage.class);
			if(facesMessage == null)
				continue;
			ComponentHelper componentHelper = DependencyInjection.inject(ComponentHelper.class);
			if(CollectionHelper.contains(renderTypes, RenderType.INLINE))
				facesContext.addMessage(componentHelper.getGlobalMessagesOwnerInlineComponentClientIdentifier(), facesMessage);
			if(CollectionHelper.contains(renderTypes, RenderType.DIALOG))
				facesContext.addMessage(componentHelper.getGlobalMessagesOwnerDialogComponentClientIdentifier(), facesMessage);
			if(CollectionHelper.contains(renderTypes, RenderType.GROWL))
				facesContext.addMessage(componentHelper.getGlobalMessagesOwnerGrowlComponentClientIdentifier(), facesMessage);
		}		
	}	
}
