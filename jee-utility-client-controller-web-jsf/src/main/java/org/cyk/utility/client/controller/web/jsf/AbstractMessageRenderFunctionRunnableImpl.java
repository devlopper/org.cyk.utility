package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderType;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessageRenderTypeGrowl;
import org.cyk.utility.client.controller.message.MessageRenderTypeInline;

public abstract class AbstractMessageRenderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<MessageRender> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AbstractMessageRenderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				for(Object  index : getFunction().getMessages()) {
					FacesMessage facesMessage = (FacesMessage) index;
					MessageRenderType renderType = getFunction().getType();
					if(renderType == null)
						renderType = __inject__(MessageRenderTypeInline.class);
					__run__(facesMessage, renderType);	
				}
			}
		});
	}
	
	protected void __run__(FacesMessage facesMessage,MessageRenderType renderType) {
		FacesContext facesContext = __injectFacesContext__();
		if(renderType instanceof MessageRenderTypeInline)
			facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerInlineComponentClientIdentifier(), facesMessage);
		else if(renderType instanceof MessageRenderTypeDialog)
			facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerDialogComponentClientIdentifier(), facesMessage);
		else if(renderType instanceof MessageRenderTypeGrowl)
			facesContext.addMessage(__injectComponentHelper__().getGlobalMessagesOwnerGrowlComponentClientIdentifier(), facesMessage);
	}
	
}