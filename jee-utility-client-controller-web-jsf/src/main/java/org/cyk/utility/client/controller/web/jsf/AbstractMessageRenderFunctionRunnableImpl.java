package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderType;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessageRenderTypeGrowl;
import org.cyk.utility.client.controller.message.MessageRenderTypeInline;
import org.cyk.utility.client.controller.message.MessageRenderTypes;
import org.cyk.utility.client.controller.message.MessagesBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.object.Objects;

public abstract class AbstractMessageRenderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<MessageRender> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AbstractMessageRenderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Objects messages = getFunction().getMessages();
				
				Notifications notifications = getFunction().getNotifications();
				if(__inject__(CollectionHelper.class).isNotEmpty(notifications)) {
					if(__inject__(CollectionHelper.class).isEmpty(messages))
						messages = __inject__(Objects.class);
					messages.add(__inject__(MessagesBuilder.class).addNotifications(notifications.get()).execute().getOutput());
				}
				
				NotificationBuilders notificationBuilders = getFunction().getNotificationBuilders();
				if(__inject__(CollectionHelper.class).isNotEmpty(notificationBuilders)) {
					if(__inject__(CollectionHelper.class).isEmpty(messages))
						messages = __inject__(Objects.class);
					notifications = __inject__(Notifications.class);
					for(NotificationBuilder index : notificationBuilders.get())
						notifications.add(index.execute().getOutput());
					messages.add(__inject__(MessagesBuilder.class).addNotifications(notifications.get()).execute().getOutput());
				}
				
				if(__inject__(CollectionHelper.class).isNotEmpty(messages)) {
					for(Object  index : messages.get()) {
						FacesMessage facesMessage = (FacesMessage) index;
						MessageRenderTypes renderTypes = getFunction().getTypes();
						if(__inject__(CollectionHelper.class).isEmpty(renderTypes)) {
							if(renderTypes == null)
								renderTypes = __inject__(MessageRenderTypes.class);
							renderTypes.add(__inject__(MessageRenderTypeDialog.class),__inject__(MessageRenderTypeInline.class));
						}
						for(MessageRenderType indexMessageRenderType : renderTypes.get())
							__run__(facesMessage, indexMessageRenderType);	
					}	
				}
			}
		});
	}
	
	protected void __run__(FacesMessage facesMessage,MessageRenderType renderType) {
		FacesContext facesContext = (FacesContext) getFunction().getProperties().getContext();
		if(facesContext == null)
			facesContext = __injectFacesContext__();
		if(facesContext == null) {
			System.err.println("Cannot render message because facesContext is null");
		}else {
			if(renderType instanceof MessageRenderTypeInline)
				facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerInlineComponentClientIdentifier(), facesMessage);
			else if(renderType instanceof MessageRenderTypeDialog)
				facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerDialogComponentClientIdentifier(), facesMessage);
			else if(renderType instanceof MessageRenderTypeGrowl)
				facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerGrowlComponentClientIdentifier(), facesMessage);	
		}
		//facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerDialogComponentClientIdentifier(), facesMessage);
	}
	
}