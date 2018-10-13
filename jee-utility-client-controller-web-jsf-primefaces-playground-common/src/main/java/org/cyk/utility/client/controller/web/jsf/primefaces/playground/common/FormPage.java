package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.form.Form;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FormPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private Form form01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
	}
	
}
