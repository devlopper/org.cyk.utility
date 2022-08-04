package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import javax.faces.component.html.HtmlOutputText;

import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.web.ValueExpressionMap;

public class OutputTextBuilderImpl extends AbstractUIComponentBuilderImpl<HtmlOutputText,OutputStringText> implements OutputTextBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected HtmlOutputText __execute__(OutputStringText outputStringText, ValueExpressionMap valueExpressionMap) throws Exception {
		HtmlOutputText outputText = new HtmlOutputText();
		outputText.setValue(outputStringText.getValue());
		return outputText;
	}
	
}
