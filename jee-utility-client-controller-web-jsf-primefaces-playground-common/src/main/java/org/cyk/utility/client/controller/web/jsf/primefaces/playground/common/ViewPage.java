package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.view.View;
import org.cyk.utility.client.controller.view.ViewBuilder;
import org.cyk.utility.client.controller.view.ViewTypeForm;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ViewPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private View view01;
	private View viewForm01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		view01 = createView01();
	}
	
	private View createView01() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.setType(__inject__(ViewTypeForm.class));
		viewBuilder.setNameOutputPropertyValue("Mon titre de formulaire");
		viewBuilder.addInputStringLineOneBuilder(Boolean.TRUE,"Firstname");
		viewBuilder.addInputStringLineOneBuilder(Boolean.FALSE,"Lastnames");
		viewBuilder.addInputStringLineManyBuilder(Boolean.TRUE,"Other details");
		/*
		viewBuilder.addProcessingCommandableButtonBuilder("Process", SystemActionCreate.class, new Runnable() {	
			@Override
			public void run() {
				System.out.println("Process");
			}
		});
		
		viewBuilder.addProcessingCommandableButtonBuilder("Cancel", SystemActionCreate.class, new Runnable() {	
			@Override
			public void run() {
				System.out.println("Cancel");
			}
		});
		*/
		return viewBuilder.execute().getOutput();
	}
}
