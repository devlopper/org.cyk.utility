package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.SelectOneRadio;
import org.cyk.utility.playground.client.controller.api.PersonTypeController;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneRadioPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private SelectOneRadio selectOneRadio;
	private CommandButton commandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		selectOneRadio = SelectOneRadio.build(SelectOneRadio.FIELD_CHOICES,__inject__(PersonTypeController.class).read());
		commandButton = Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Enregistrer"));
		commandButton.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Value : "+selectOneRadio.getValue());
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Radio";
	}
}