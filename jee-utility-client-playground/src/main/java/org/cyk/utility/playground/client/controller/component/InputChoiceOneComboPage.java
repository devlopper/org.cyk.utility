package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.SelectOneCombo;
import org.cyk.utility.playground.client.controller.api.PersonTypeController;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneComboPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private SelectOneCombo selectOneCombo;
	private CommandButton commandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		selectOneCombo = SelectOneCombo.build(SelectOneCombo.FIELD_CHOICES,__inject__(PersonTypeController.class).read());
		
		selectOneCombo.getAjaxes().get("itemSelect").setDisabled(Boolean.FALSE).setListener(new Ajax.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				Object v = FieldHelper.read(argument, "source.value");
				System.out.println(
						"ITEM SELECT : "+v+" : "+v.getClass().getSimpleName());
			}
		});
		selectOneCombo.getAjaxes().get("valueChange").setDisabled(Boolean.FALSE).setListener(new Ajax.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				Object v = FieldHelper.read(argument, "source.value");
				System.out.println(
						"VALUE CHANGE : "+v+" : "+v.getClass().getSimpleName());
			}
		});
		
		commandButton = Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Enregistrer"));
		commandButton.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("Value : "+selectOneCombo.getValue());
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Combo";
	}
}