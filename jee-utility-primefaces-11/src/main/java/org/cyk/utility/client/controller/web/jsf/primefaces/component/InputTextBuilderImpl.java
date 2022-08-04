package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.primefaces.component.inputtext.InputText;

public class InputTextBuilderImpl extends AbstractUIComponentBuilderImpl<InputText,InputStringLineOne> implements InputTextBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected InputText __execute__(InputStringLineOne inputStringLineOne, ValueExpressionMap valueExpressionMap) throws Exception {
		InputText inputText = new InputText();
		inputText.setId(inputStringLineOne.getIdentifier().toString());
		//inputText.setRequired(Boolean.TRUE);
		return inputText;
	}
	
}
