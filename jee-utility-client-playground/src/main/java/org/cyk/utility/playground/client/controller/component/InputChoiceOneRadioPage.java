package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.SelectOneRadio;
import org.cyk.utility.playground.client.controller.api.PersonTypeController;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneRadioPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private SelectOneRadio selectOneRadio,selectOneRadioWithDefault;
	private CommandButton commandButton;
	private Collection<PersonType> personTypes;
	private Collection<String> strings = List.of("One","Two","Three");
	private String string = "One";
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		personTypes = __inject__(PersonTypeController.class).read();
		selectOneRadio = SelectOneRadio.build(SelectOneRadio.FIELD_CHOICES,personTypes);
		selectOneRadio.getAjaxes().get("change").setDisabled(Boolean.FALSE).setListener(new Ajax.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Selected 1 : "+FieldHelper.read(argument, "source.value")+" : "+FieldHelper.read(argument, "source.value").getClass()
						.getSimpleName(),RenderType.GROWL);
				//System.out.println("Radio selected : "+FieldHelper.read(argument, "source.value"));
			}
		});
		
		selectOneRadioWithDefault = SelectOneRadio.build(SelectOneRadio.FIELD_CHOICES,personTypes);
		selectOneRadioWithDefault.setValue(CollectionHelper.getFirst(selectOneRadioWithDefault.getChoices()));
		
		selectOneRadioWithDefault.getAjaxes().get("change").setDisabled(Boolean.FALSE).setListener(new Ajax.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Selected 2 : "+FieldHelper.read(argument, "source.value")+" : "+FieldHelper.read(argument, "source.value").getClass()
						.getSimpleName(),RenderType.GROWL);
				//System.out.println("Radio selected : "+FieldHelper.read(argument, "source.value"));
			}
		});
		
		commandButton = Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Enregistrer"));
		commandButton.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Value 1 : "+selectOneRadio.getValue());
				MessageRenderer.getInstance().render(" - Value 2 : "+selectOneRadioWithDefault.getValue());
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Radio";
	}
}