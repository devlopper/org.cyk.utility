package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneAutoCompletePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private AutoComplete autoCompleteController;
	private AutoComplete autoCompleteReader;
	private CommandButton commandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		autoCompleteController = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		autoCompleteReader = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		autoCompleteReader.setReaderUsable(Boolean.TRUE);
		commandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Enregistrer");
		commandButton.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				MessageRenderer.getInstance().render("Value 01 : "+autoCompleteController.getValue());
				MessageRenderer.getInstance().render(" - Value 02 : "+autoCompleteController.getValue());
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Auto Complete";
	}
}