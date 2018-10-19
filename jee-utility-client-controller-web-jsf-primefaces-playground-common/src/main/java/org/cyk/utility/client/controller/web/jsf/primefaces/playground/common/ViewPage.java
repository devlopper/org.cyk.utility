package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandButtonBuilder;
import org.cyk.utility.client.controller.view.View;
import org.cyk.utility.client.controller.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ViewPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private View view01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.setNameOutputPropertyValue("Mon titre de formulaire");
		viewBuilder.addInputStringLineOneBuilder(Boolean.TRUE,"Firstname");
		viewBuilder.addInputStringLineOneBuilder(Boolean.FALSE,"Lastnames");
		viewBuilder.addInputStringLineManyBuilder(Boolean.TRUE,"Other details");
		
		viewBuilder.addProcessingCommandableBuilder(CommandButtonBuilder.class, "Process", SystemActionCreate.class, new Runnable() {	
			@Override
			public void run() {
				System.out.println("Process");
			}
		});
		
		viewBuilder.addProcessingCommandableBuilder(CommandButtonBuilder.class, "Cancel", SystemActionCreate.class, new Runnable() {	
			@Override
			public void run() {
				System.out.println("Cancel");
			}
		});
		
		view01 = viewBuilder.execute().getOutput();
	}
	
}
