package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.Password;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputPasswordPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Password password01;
	private Password password02;
	private CommandButton commandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		password02 = Password.build();
		password01 = Password.build(Password.FIELD_MATCH,password02.getIdentifier());
		commandButton = Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Envoyer"));
		commandButton.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Value : "+password01.getValue()+" = "+password02.getValue());
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Password";
	}
	
}
