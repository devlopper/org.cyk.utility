package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneAutoCompletePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private AutoComplete autoComplete;
	private CommandButton commandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		autoComplete = Builder.build(AutoComplete.class,Map.of(AutoComplete.FIELD_ENTITY_CLASS,Namable.class));
		commandButton = Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Enregistrer"));
		commandButton.setListener(new CommandButton.Listener() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Value : "+autoComplete.getValue());
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Auto Complete";
	}

}
