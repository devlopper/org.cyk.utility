package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandableButtonPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private CommandableButton noServerNoClient;
	@Inject private CommandableButton serverOnlyNoConfirmation,serverThenClientNoConfirmation;
	@Inject private CommandableButton clientOnlyNoConfirmation,clientThenServerNoConfirmation;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		noServerNoClient.getProperties().setValue("No Server No Client");
	
		serverOnlyNoConfirmation.getProperties().setValue("Server Only No Confirmation");
		serverOnlyNoConfirmation.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(SystemActionCreate.class));
		serverOnlyNoConfirmation.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Server Only No Confirmation");
			}
		});
	}
	
	public void executeCommandSimple() {
		System.out.println("CommandablePage.executeCommandSimple() 0");
	}
}
