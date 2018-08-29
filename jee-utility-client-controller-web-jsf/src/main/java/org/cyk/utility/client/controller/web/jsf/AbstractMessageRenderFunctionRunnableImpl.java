package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderType;
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
					__run__(facesMessage, renderType);	
				}
			}
		});
	}
	
	protected void __run__(FacesMessage facesMessage,MessageRenderType renderType) {
		if(renderType instanceof MessageRenderTypeInline)
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			//__inject__(FacesContext.class).addMessage(null, facesMessage);
	}
	
}