package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AutoComplete;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneAutoCompletePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private AutoComplete autoCompleteController;
	private AutoComplete autoCompleteReader;
	private AutoComplete autoCompleteSelectItem;
	private AutoComplete autoCompleteSelectItemInDialog;
	private Dialog dialog;
	private CommandButton commandButton,showDialogCommandButton;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		autoCompleteController = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		autoCompleteReader = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		autoCompleteReader.setReaderUsable(Boolean.TRUE);
		
		autoCompleteSelectItem = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		autoCompleteSelectItem.enableAjaxItemSelect();
		autoCompleteSelectItem.getAjaxes().get("itemSelect").setListener(new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected void run(AbstractAction action) {
				MessageRenderer.getInstance().render("Selected : "+FieldHelper.read(action.get__argument__(), "source.value"),RenderType.GROWL);
			}
		});
		
		autoCompleteSelectItemInDialog = AutoComplete.build(AutoComplete.FIELD_ENTITY_CLASS,Namable.class);
		autoCompleteSelectItemInDialog.enableAjaxItemSelect();
		autoCompleteSelectItemInDialog.getAjaxes().get("itemSelect").setListener(new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected void run(AbstractAction action) {
				MessageRenderer.getInstance().render("Selected In Dialog : "+FieldHelper.read(action.get__argument__(), "source.value"),RenderType.GROWL);
			}
		});
		
		dialog = Dialog.build(Dialog.FIELD_HEADER,"Recherche",Dialog.FIELD_WIDTH,1000,Dialog.FIELD_LISTENER,new Dialog.Listener.AbstractImpl() {
			@Override
			public Map<Object, Object> getExecuteCommandButtonArguments(Dialog dialog) {
				Map<Object, Object> arguments = super.getExecuteCommandButtonArguments(dialog);
				arguments.put(CommandButton.FIELD_VALUE, "Sélectionner");
				arguments.put(CommandButton.FIELD_USER_INTERFACE_ACTION, UserInterfaceAction.EXECUTE_FUNCTION);
				arguments.put(CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE, Boolean.TRUE);
				arguments.put(CommandButton.FIELD_LISTENER, new CommandButton.Listener.AbstractImpl() {
					@Override
					protected Object __runExecuteFunction__(AbstractAction action) {
						return null;
					}
				});
				return arguments;
			}
		},Dialog.FIELD_MODAL,Boolean.TRUE);
		dialog.setCommandButtons(null);
		
		showDialogCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Rechercher",CommandButton.FIELD_ICON,"fa fa-search",CommandButton.FIELD_PROCESS,"@this"
				,CommandButton.FIELD___DIALOG__,dialog,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.SHOW_DIALOG);
		
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