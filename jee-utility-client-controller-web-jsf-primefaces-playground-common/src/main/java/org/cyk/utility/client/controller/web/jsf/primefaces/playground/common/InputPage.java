package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.input.InputText;
import org.cyk.utility.client.controller.component.layout.LayoutGridResponsive;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private LayoutGridResponsive formLayout;
	@Inject private InputText inputText;
	@Inject private InputText inputTextRequired;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		inputText.setLabelComponentValue("Simple");
		
		inputTextRequired.getProperties().setRequired(Boolean.TRUE);
		inputTextRequired.setLabelComponentValue("Required");
	}
	
	public void show() {
		System.out.println("InputPage.show()");
		System.out.println("Input text : "+inputText.getValue());
		System.out.println("Input text required : "+inputTextRequired.getValue());
	}
}
