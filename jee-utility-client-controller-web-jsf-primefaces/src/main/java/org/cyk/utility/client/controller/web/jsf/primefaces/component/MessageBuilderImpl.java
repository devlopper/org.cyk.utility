package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.primefaces.component.message.Message;

public class MessageBuilderImpl extends AbstractUIComponentBuilderImpl<Message,OutputStringMessage> implements MessageBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Message __execute__(OutputStringMessage outputStringText, ValueExpressionMap valueExpressionMap) throws Exception {
		Message message = new Message();
		message.setId(outputStringText.getIdentifier().toString());
		message.setFor("form:dataTable:0:"+outputStringText.getProperties().getFor().toString());
		//message.setDisplay(outputStringText.getProperties().getDisplay().toString());
		return message;
	}
	
}
