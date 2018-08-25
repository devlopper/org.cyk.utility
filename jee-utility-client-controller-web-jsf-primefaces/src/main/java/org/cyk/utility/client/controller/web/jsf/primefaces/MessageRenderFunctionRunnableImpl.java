package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.message.MessageRenderType;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessageRenderTypeGrowl;
import org.cyk.utility.client.controller.web.jsf.AbstractMessageRenderFunctionRunnableImpl;
import org.primefaces.PrimeFaces;

public class MessageRenderFunctionRunnableImpl extends AbstractMessageRenderFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __run__(FacesMessage facesMessage, MessageRenderType renderType) {
		super.__run__(facesMessage, renderType);
		if(renderType instanceof MessageRenderTypeDialog)
			PrimeFaces.current().dialog().showMessageDynamic(facesMessage);
		else if(renderType instanceof MessageRenderTypeGrowl)
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
}