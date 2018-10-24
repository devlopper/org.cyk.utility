package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableButtonBuilder;
import org.cyk.utility.client.controller.component.form.Form;
import org.cyk.utility.client.controller.component.form.FormBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FormPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Form form01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		FormBuilder formBuilder = __inject__(FormBuilder.class);
		
		//formBuilder.getView(Boolean.TRUE).setNameOutputPropertyValue("Mon titre de formulaire");
		formBuilder.getView(Boolean.TRUE).addInputStringLineOneBuilder(Boolean.TRUE,"Firstname");
		formBuilder.getView(Boolean.TRUE).addInputStringLineOneBuilder(Boolean.FALSE,"Lastnames");
		formBuilder.getView(Boolean.TRUE).addInputStringLineManyBuilder(Boolean.TRUE,"Other details");
		
		formBuilder.getView(Boolean.TRUE).addProcessingCommandableBuilder(CommandableButtonBuilder.class, "Process", SystemActionCreate.class, new Runnable() {	
			@Override
			public void run() {
				System.out.println("Process");
			}
		});
		
		formBuilder.getView(Boolean.TRUE).addProcessingCommandableBuilder(CommandableButtonBuilder.class, "Cancel", SystemActionCreate.class, new Runnable() {	
			@Override
			public void run() {
				System.out.println("Cancel");
			}
		});
		
		form01 = formBuilder.execute().getOutput();
	}
	
}
